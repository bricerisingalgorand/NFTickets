package com.pago.core.quotes.smartcontract;

import com.algorand.algosdk.crypto.TEALProgram;
import com.algorand.algosdk.util.Encoder;
import com.algorand.algosdk.v2.client.algod.TealCompile;
import com.algorand.algosdk.v2.client.common.AlgodClient;
import com.algorand.algosdk.v2.client.common.Response;
import com.algorand.algosdk.v2.client.model.CompileResponse;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.pago.core.quotes.api.EventResourceException;
import com.pago.core.quotes.dao.models.EventItem;
import com.pago.core.transactiongateway.api.transaction.dto.*;
//import com.pago.crowdfund.dao.model.Escrow;
//import com.pago.crowdfund.dao.model.Fund;
//import com.pago.crowdfund.dao.model.FundState;
//import com.pago.crowdfund.dao.service.CrowdFundException;
//import com.pago.crowdfund.dao.service.CrowdFundServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.UUID;

/**
 * Event Contract Factory
 *     Create the Event Contract Factory
 * # Directory of this bash program
 * ACCOUNT=$(${gcmd} account list|awk '{ print $3 }'|head -n 1)
 * APPID=$(${gcmd} app create --creator ${ACCOUNT} --approval-prog ./bticketing.teal --global-byteslices 64 --global-ints 0 --local-byteslices 0 --local-ints 1  --clear-prog ./clear.teal | grep Created | awk '{ print $6 }')
 * echo "App ID="$APPID
 * ${gcmd} app read --app-id $APPID --guess-format --global --from $ACCOUNT
 * #optin the creator account
 * ${gcmd} app optin --app-id $APPID --from $ACCOUNT
 * # need to opt in second account to new asset id
 * # need to send one vote token to account 2
 * #gcmd2="../../goal -d ../test/Node"
 * #ACCOUNT2=$(${gcmd2} account list|awk '{ print $3 }'|head -n 1)
 * echo "ACCOUNT="$ACCOUNT
 *
 */
public class EventContractFactory {

    private final CrowdFundServiceImpl cfsImpl;
    public final static String EVENT_APPLICATION_ID = "NFTickets";
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final MustacheFactory mf = new DefaultMustacheFactory();

    private final String approvalProgramFile = "bticketing.teal";
    private final String closeProgramFile = "clear.teal";
    private final long globalBytes = 64L;
    private final long globalInts = 0L;
    private final long localBytes = 0L;
    private final long localInts = 1L;

    public EventContractFactory(CrowdFundServiceImpl cfsImpl) {
        this.cfsImpl = cfsImpl;
    }

    public EventItem createCrowdFund(AlgodClient algodClient, EventItem event) throws EventResourceException {

        // Create the app create request
        AppCreateRequest appCreateRequest = appCreateFund(fund);

        event.setEventState(EventState.CREATED);

        AgreementRequest agreementRequest = new AgreementRequest();
        agreementRequest.setDescription("Create crowdfund request for fund " + fund.getName() );
        agreementRequest.setBody(fund.toString());
        agreementRequest.setMediaType("text");

        TransactionEnvelopeRequest transactionEnvelopeRequest = new TransactionEnvelopeRequest();
        transactionEnvelopeRequest.setName("CREATE:"+fund.getId());
        transactionEnvelopeRequest.setDescription("Transaction envelope for create fund " + fund.getName());
        transactionEnvelopeRequest.setApplicationId(EVENT_APPLICATION_ID);
        transactionEnvelopeRequest.setSender(fund.getCreatorAddress());
        transactionEnvelopeRequest.setOriginator(fund.getCreatorAddress());

        transactionEnvelopeRequest.setTransactionInterfaceRequest(appCreateRequest);
        transactionEnvelopeRequest.setCallbackURL("http://localhost:9000/crowdfund/callback?state=create,fund="+ fund.getId());

        cfsImpl.transactionGatewayClient.submitTransactionEnvelope(transactionEnvelopeRequest);

        return fund;
    }

// APPID=$(${gcmd} app create --creator ${ACCOUNT} --approval-prog ./bticketing.teal --global-byteslices 64 --global-ints 0 --local-byteslices 0 --local-ints 1  --clear-prog ./clear.teal | grep Created | awk '{ print $6 }')
    private AppCreateRequest appCreateEvent(EventItem event) throws EventResourceException {
        try {

            log.info("creating event: '" + event.getPerformance().getName() + "'");
            byte[] approvalProgram = loadResource(approvalProgramFile);
            byte[] closeProgram = loadResource(closeProgramFile);

//            event.setId(UUID.randomUUID().toString());

            String args = "int:" + event.getStartTime().getMillis() + ",int:" + event.getEndTime().getMillis() + ",int:" + event.getVenue().getTotalSeats() + ",addr:" + event.getEventOwnerAccount();
            log.info("create event args: " + args);

            AgreementRequest agreementRequest = new AgreementRequest();
            agreementRequest.setDescription("agreement for create event " + event.getId());
            agreementRequest.setMediaType("text");
            agreementRequest.setBody("you agree to create the event '" + event.getPerformance().getName() + "'");

            AppCreateRequest appCreateRequest = new AppCreateRequest();
            appCreateRequest.setSender(event.getEventOwnerAccount());
            appCreateRequest.setFee(BigInteger.valueOf(1000));
            appCreateRequest.setLease("ac-"+event.getId());
            appCreateRequest.setApplicationArgs(args);
            appCreateRequest.setCreatorAddress(event.getEventOwnerAccount());
            appCreateRequest.setApprovalProgram(approvalProgram);
            appCreateRequest.setCloseProgram(closeProgram);
            appCreateRequest.setGlobalBytes(globalBytes);
            appCreateRequest.setLocalBytes(localBytes);
            appCreateRequest.setGlobalInts(globalInts);
            appCreateRequest.setLocalInts(localInts);
            appCreateRequest.setAgreementRequest(agreementRequest);

            return appCreateRequest;

        } catch (Exception e) {
            String message = "error in create App Creation Transaction Envelope " + e.getMessage();
            log.error(message);
            throw new EventResourceException( message);
        }
    }

//    public void processUpdateCrowdFund(AlgodClient algodClient, Fund fund) throws CrowdFundException {
//        AppUpdateRequest appUpdateRequest = updateCrowdFund(algodClient, fund);
//        AppOptInRequest appOptInRequest = optInCrowdFund(  fund);
//
//
//        AgreementRequest agreementRequest = new AgreementRequest();
//        agreementRequest.setDescription("Update and OptIn crowdfund request for fund " + fund.getName() );
//        agreementRequest.setBody(fund.toString());
//        agreementRequest.setMediaType("text");
//
//        GroupTransactionRequest groupTransactionRequest = new GroupTransactionRequest();
//        groupTransactionRequest.setSender(fund.getCreatorAddress());
//        groupTransactionRequest.setFee(BigInteger.valueOf(1000));
//        groupTransactionRequest.setAgreementRequest(agreementRequest);
//        groupTransactionRequest.getTransactionInterfaceMapRequest().put("app update" , appUpdateRequest);
//        groupTransactionRequest.getTransactionInterfaceMapRequest().put("app optin" , appOptInRequest);
//
//        TransactionEnvelopeRequest transactionEnvelopeRequest = new TransactionEnvelopeRequest();
//        transactionEnvelopeRequest.setName("CREATE:"+fund.getId());
//        transactionEnvelopeRequest.setDescription("Transaction envelope for create fund " + fund.getName());
//        transactionEnvelopeRequest.setApplicationId(CrowdFundUtility.CROWDFUND_APPLICATION_ID);
//        transactionEnvelopeRequest.setSender(fund.getCreatorAddress());
//        transactionEnvelopeRequest.setOriginator(fund.getCreatorAddress());
//
//        transactionEnvelopeRequest.setTransactionInterfaceRequest(groupTransactionRequest);
//        transactionEnvelopeRequest.setCallbackURL("http://localhost:9000/crowdfund/callback?state=update,fund="+ fund.getId());
//
//        fund.setFundState(FundState.UPDATED);
//
//
//        cfsImpl.transactionGatewayClient.submitTransactionEnvelope(transactionEnvelopeRequest);
//
//
//    }

//    // UPDATE=$(${gcmd} app update --app-id=${APPID} --from ${ACCOUNT}  --approval-prog ./crowd_fund.teal   --clear-prog ./crowd_fund_close.teal --app-arg "addr:F4HJHVIPILZN3BISEVKXL4NSASZB4LRB25H4WCSEENSPCJ5DYW6CKUVZOA" )
//    // Update CrowdFund is called after the create crowd fund transaction is complete.
//    private AppUpdateRequest updateCrowdFund(AlgodClient algodClient, Fund fund) throws CrowdFundException {
//        try {
//
//            byte[] approvalProgram = loadResource(approvalProgramFile);
//            byte[] closeProgram = loadResource(closeProgramFile);
//
//            fund.setEscrow(buildEscrow(algodClient, fund));
//            String args = "addr:" + fund.getEscrow().getAddress();
//
//            AgreementRequest agreementRequest = new AgreementRequest();
//            agreementRequest.setDescription("agreement for update fund " + fund.getId());
//            agreementRequest.setMediaType("text");
//            agreementRequest.setBody("you agree to update the fund '" + fund.getName() + "'");
//
//            AppUpdateRequest appUpdateRequest = new AppUpdateRequest();
//
//            appUpdateRequest.setType("AppUpdateRequest");
//            appUpdateRequest.setSender(fund.getCreatorAddress());
//
//            appUpdateRequest.setFee(BigInteger.valueOf(1000));
//            appUpdateRequest.setLease("au-" + fund.getId());
//            appUpdateRequest.setApplicationId(fund.getAppId());
//            appUpdateRequest.setCreatorAddress(fund.getCreatorAddress());
//            appUpdateRequest.setApplicationArgs(args);
//            appUpdateRequest.setApprovalProgram(approvalProgram);
//            appUpdateRequest.setCloseProgram(closeProgram);
//            appUpdateRequest.setAgreementRequest(agreementRequest);
//
//            return appUpdateRequest;
//
//        } catch (Exception e) {
//            String message = "error in update crowd fund " + e.getMessage();
//            log.error(message);
//            throw new CrowdFundException(500, message);
//        }
//    }

    // OptIn CrowdFund
    // ${gcmd} app optin  --app-id ${APPID} --from $ACCOUNT
    private AppOptInRequest optInCrowdFund( Fund fund) throws EventResourceException {

        try {
            AgreementRequest agreementRequest = new AgreementRequest();
            agreementRequest.setMediaType("text");
            agreementRequest.setBody("ApplicationOptInt for fund '" + fund.getName() + "' and creator '" + fund.getCreatorAddress() + "'");
            agreementRequest.setDescription("ApplicationOptInt for fund " + fund.getId());

            AppOptInRequest appOptInRequest = new AppOptInRequest();
            appOptInRequest.setType("AppOptInRequest");
            appOptInRequest.setApplicationId(fund.getAppId());
            appOptInRequest.setSender(fund.getCreatorAddress());
            appOptInRequest.setAgreementRequest(agreementRequest);
            appOptInRequest.setFee(BigInteger.valueOf(1000));
            appOptInRequest.setLease("ao-" + fund.getId());

            return appOptInRequest;
        } catch (Exception e) {
            String message = "error in app opt in " + e.getMessage();
            log.error(message);
            throw new EventResourceException(  message);
        }
    }

//    private Escrow buildEscrow(AlgodClient algodClient, Fund fund) throws CrowdFundException, IOException {
//        Escrow escrow = new Escrow();
//        HashMap<String, Object> scopes = new HashMap();
//        scopes.put("applicationId", fund.getAppId());
//
//        Mustache m = mf.compile(escrowFile);
//        StringWriter writer = new StringWriter();
//        m.execute(writer, scopes).flush();
//
//        String escrowFileString = writer.toString();
//
//        CompileResponse compiledEscrow = compileTealProgram(algodClient, escrowFileString.getBytes());
//        escrow.setAddress(compiledEscrow.hash);
//        escrow.setTealProgram(createTealProgram(compiledEscrow));
//        return escrow;
//    }

    public CompileResponse compileTealProgram(AlgodClient algodClient, byte[] file) throws EventResourceException {
        try {
            TealCompile tealCompiler = new TealCompile(algodClient);

            tealCompiler = tealCompiler.source(file);
            Response<CompileResponse> execute = tealCompiler.execute(cfsImpl.getHeaderNames(), cfsImpl.getHeaderValues());
            return execute.body();
        } catch (Exception e) {
            String message = "error in load TEAL Program From File " + e.getMessage();
            log.error(message);
            throw new EventResourceException( message);
        }
    }

    public CompileResponse compileTealProgram(AlgodClient algodClient, String file) throws EventResourceException {
        try {
            byte[] resourceBytes = loadResource(file);
            return compileTealProgram(algodClient, resourceBytes);
        } catch (Exception e) {
            String message = "error in load TEAL Program From File " + e.getMessage();
            log.error(message);
            throw new EventResourceException( message);
        }
    }

    public TEALProgram createTealProgram(CompileResponse compileResponse) throws EventResourceException {
        byte[] compiledBytes = Encoder.decodeFromBase64(compileResponse.result);
        return new TEALProgram(compiledBytes);
    }

    public byte[] loadResource(String file) throws EventResourceException {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream fis = classLoader.getResourceAsStream(file);
            if (fis == null) {
                throw  new FileNotFoundException("could not find file: '" + file + "'");
            }
            byte[] data = new byte[fis.available()];
            fis.read(data);
            return data;
        } catch (IOException e) {
            String message = "error in load resource " + e.getMessage();
            log.error(message);
            throw new EventResourceException( message);
        }
    }

}
