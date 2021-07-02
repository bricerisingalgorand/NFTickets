package com.pago.core.quotes.smartcontract;

import com.google.inject.Inject;
import com.pago.core.quotes.api.EventResourceException;
import com.pago.core.quotes.dao.models.TicketPurchase;
import com.pago.core.transactiongateway.api.transaction.dto.*;
import com.pago.core.transactiongateway.client.TransactionGatewayClient;
import com.pago.core.quotes.config.AlgodConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;


/**
 ${gcmd} app call --app-id 1  --app-arg "str:buy" --app-arg "int:2000"  --from $ACCOUNT --out=dump1.dr --dryrun-dump
 ../../tealdbg debug bticketing.teal -d dump1.dr
 */
public class TicketPurchaseFactory {

    public final static String EVENT_APPLICATION_ID = "NFTickets";
    private final Logger log = LoggerFactory.getLogger(getClass().getName());

    @Inject
    public TransactionGatewayClient transactionGatewayClient;

    @Inject
    public AlgodConfiguration algodConfiguration;

    public TicketPurchaseFactory() {

    }

    public TicketPurchase createTicketPurchase(TicketPurchase ticketPurchase) throws EventResourceException {


        TransactionEnvelopeRequest transactionEnvelopeRequest = new TransactionEnvelopeRequest();
        transactionEnvelopeRequest.setName("ticket-purchase-" + ticketPurchase.getId());
        transactionEnvelopeRequest.setDescription("Transaction envelope for ticket purchase");
        transactionEnvelopeRequest.setApplicationId(EVENT_APPLICATION_ID);

        AppOptInRequest appOptInRequest = createAppOptIn(ticketPurchase);

        AppCallRequest appCallInvestTransferRequest = createAppCallInvest(ticketPurchase);

        TransferRequest sendInvestmentTransferRequest = createSendInvestment(ticketPurchase);

        // TODO determine if account has already opted into the application
        boolean requiresOptIn = true;

        GroupTransactionRequest groupTransactionRequest = new GroupTransactionRequest();

        if (requiresOptIn) {
            groupTransactionRequest.getTransactionInterfaceMapRequest().put("optinRequest", appOptInRequest);
        }
        groupTransactionRequest.getTransactionInterfaceMapRequest().put("appCallInvest",appCallInvestTransferRequest);
        groupTransactionRequest.getTransactionInterfaceMapRequest().put("sendInvestment", sendInvestmentTransferRequest);

        transactionEnvelopeRequest.setTransactionInterfaceRequest(groupTransactionRequest);

        transactionGatewayClient.submitTransactionEnvelope(transactionEnvelopeRequest);

        return ticketPurchase;
    }

    private AppOptInRequest createAppOptIn(TicketPurchase ticketPurchase) throws EventResourceException {
        try {

            AgreementRequest agreementRequest = new AgreementRequest();
            agreementRequest.setDescription("agreement to optin to  event " + ticketPurchase.getEventId());
            agreementRequest.setMediaType("text");
            agreementRequest.setBody("you agree to optin for event " + ticketPurchase.getEventId()  );


            AppOptInRequest appOptInRequest = new AppOptInRequest();
            appOptInRequest.setApplicationId(ticketPurchase.getAppId());
            appOptInRequest.setSender(ticketPurchase.getTicketPurchaseAccount());
            appOptInRequest.setAgreementRequest(agreementRequest);

            return appOptInRequest;
        } catch (Exception e) {
            String message = "error in app opt in " + e.getMessage();
            log.error(message);
            throw new EventResourceException( message);
        }
    }

    private AppCallRequest createAppCallInvest( TicketPurchase ticketPurchase)
            throws   EventResourceException {
        try {
            String args = "str:donate";

            AgreementRequest agreementRequest = new AgreementRequest();
            agreementRequest.setDescription("agreement to purchase ticket for event " + ticketPurchase.getEventId());
            agreementRequest.setMediaType("text");
            agreementRequest.setBody("you agree to purchase ticket for event " + ticketPurchase.getEventId()  );

            AppCallRequest applicationCallRequest = new AppCallRequest();
            applicationCallRequest.setApplicationId(ticketPurchase.getAppId());
            applicationCallRequest.setApplicationArgs(args);
            applicationCallRequest.setSender(ticketPurchase.getTicketPurchaseAccount());
            applicationCallRequest.setAgreementRequest(agreementRequest);

            return applicationCallRequest;
        } catch (Exception e) {
            String message = "error in create App Call Request " + e.getMessage();
            log.error(message);
            throw new EventResourceException( message);
        }
    }

    // goal clerk send --from={ACCOUNT} --to="F4HJHVIPILZN3BISEVKXL4NSASZB4LRB25H4WCSEENSPCJ5DYW6CKUVZOA" --amount=500000 --out=unsignedtransaction2.tx -d ~/node/data
    private TransferRequest createSendInvestment(TicketPurchase ticketPurchase) throws EventResourceException {
        try {

            AgreementRequest agreementRequest = new AgreementRequest();
            agreementRequest.setDescription("agreement to purchase ticket for event " + ticketPurchase.getEventId());
            agreementRequest.setMediaType("text");
            agreementRequest.setBody("you agree to purchase ticket for event " + ticketPurchase.getEventId()  );

            TransferRequest transferRequest = new TransferRequest();
            transferRequest.setSender(ticketPurchase.getTicketPurchaseAccount());
            transferRequest.setAmount(BigInteger.valueOf(ticketPurchase.getPrice()));
            transferRequest.setReceiver(ticketPurchase.getEventAccount());
            transferRequest.setAssetId(ticketPurchase.getAssetId());
            transferRequest.setLease(ticketPurchase.getSeatNumber().substring(0,30));
            transferRequest.setAgreementRequest(agreementRequest);
            return transferRequest;

        } catch (Exception e) {
            String message = "error in send investment " + e.getMessage();
            log.error(message);
            throw new EventResourceException( message);
        }
    }
}
