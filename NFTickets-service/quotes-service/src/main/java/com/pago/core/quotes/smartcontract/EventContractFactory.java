package com.pago.core.quotes.smartcontract;

import com.algorand.algosdk.crypto.TEALProgram;
import com.algorand.algosdk.util.Encoder;
import com.algorand.algosdk.v2.client.algod.TealCompile;
import com.algorand.algosdk.v2.client.common.AlgodClient;
import com.algorand.algosdk.v2.client.common.Response;
import com.algorand.algosdk.v2.client.model.CompileResponse;
import com.github.mustachejava.DefaultMustacheFactory;

import com.github.mustachejava.MustacheFactory;
import com.google.inject.Inject;
import com.pago.core.quotes.api.EventResourceException;
import com.pago.core.quotes.dao.models.EventItem;
import com.pago.core.transactiongateway.api.transaction.dto.*;
import com.pago.core.transactiongateway.client.TransactionGatewayClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.math.BigInteger;


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

//    private final CrowdFundServiceImpl cfsImpl;
    public final static String EVENT_APPLICATION_ID = "NFTickets";
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final MustacheFactory mf = new DefaultMustacheFactory();

    private final String approvalProgramFile = "bticketing.teal";
    private final String closeProgramFile = "clear.teal";
    private final long globalBytes = 64L;
    private final long globalInts = 0L;
    private final long localBytes = 0L;
    private final long localInts = 1L;


    @Inject
    public TransactionGatewayClient transactionGatewayClient;

    @Inject
    public AlgodConfiguration algodConfiguration;


    public String[] getHeaderNames() {
        String[] headerNames = {"X-API-Key"};
        return headerNames;
    }

    public String[] getHeaderValues() {
        String[] headerValues = {algodConfiguration.algodApiTokenAuthHeader};
        return headerValues;
    }



    public EventContractFactory() {

    }

    public EventItem createEventContract(AlgodClient algodClient, EventItem event) throws EventResourceException {

        // Create the app create request
        AppCreateRequest appCreateRequest = appCreateEvent(event);

//        event.setEventState(EventState.CREATED);

        AgreementRequest agreementRequest = new AgreementRequest();
        agreementRequest.setDescription("Create crowdfund request for fund " + event.getPerformance().getName() );
        agreementRequest.setBody(event.toString());
        agreementRequest.setMediaType("text");

        TransactionEnvelopeRequest transactionEnvelopeRequest = new TransactionEnvelopeRequest();
        transactionEnvelopeRequest.setName("CREATE:"+event.getId());
        transactionEnvelopeRequest.setDescription("Transaction envelope for create fund " + event.getPerformance().getName() );
        transactionEnvelopeRequest.setApplicationId(EVENT_APPLICATION_ID);
        transactionEnvelopeRequest.setSender(event.getEventOwnerAccount());
        transactionEnvelopeRequest.setOriginator(event.getEventOwnerAccount());

        transactionEnvelopeRequest.setTransactionInterfaceRequest(appCreateRequest);
        transactionEnvelopeRequest.setCallbackURL("http://localhost:9000/event/callback?state=create,event="+ event.getId());

        transactionGatewayClient.submitTransactionEnvelope(transactionEnvelopeRequest);

        return event;
    }

// APPID=$(${gcmd} app create --creator ${ACCOUNT} --approval-prog ./bticketing.teal --global-byteslices 64 --global-ints 0 --local-byteslices 0 --local-ints 1  --clear-prog ./clear.teal | grep Created | awk '{ print $6 }')
    private AppCreateRequest appCreateEvent(EventItem event) throws EventResourceException {
        try {

            log.info("creating event: '" + event.getPerformance().getName() + "'");
            byte[] approvalProgram = loadResource(approvalProgramFile);
            byte[] closeProgram = loadResource(closeProgramFile);

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


    // OptIn CrowdFund
    // ${gcmd} app optin  --app-id ${APPID} --from $ACCOUNT
    private AppOptInRequest optInCrowdFund( EventItem event) throws EventResourceException {

        try {
            AgreementRequest agreementRequest = new AgreementRequest();
            agreementRequest.setMediaType("text");
            agreementRequest.setBody("ApplicationOptInt for event '" + event.getPerformance().getName() + "' and creator '" + event.getEventOwnerAccount() + "'");
            agreementRequest.setDescription("ApplicationOptInt for event " + event.getId());

            AppOptInRequest appOptInRequest = new AppOptInRequest();
            appOptInRequest.setType("AppOptInRequest");
            appOptInRequest.setApplicationId(event.getId());
            appOptInRequest.setSender(event.getEventOwnerAccount());
            appOptInRequest.setAgreementRequest(agreementRequest);
            appOptInRequest.setFee(BigInteger.valueOf(1000));
            appOptInRequest.setLease("ao-" + event.getId());

            return appOptInRequest;
        } catch (Exception e) {
            String message = "error in app opt in " + e.getMessage();
            log.error(message);
            throw new EventResourceException(  message);
        }
    }




    public CompileResponse compileTealProgram(AlgodClient algodClient, byte[] file) throws EventResourceException {
        try {
            TealCompile tealCompiler = new TealCompile(algodClient);

            tealCompiler = tealCompiler.source(file);
            Response<CompileResponse> execute = tealCompiler.execute(getHeaderNames(), getHeaderValues());
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
