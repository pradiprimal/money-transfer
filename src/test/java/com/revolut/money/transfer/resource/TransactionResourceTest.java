package com.revolut.money.transfer.resource;

import com.jayway.jsonpath.JsonPath;
import com.revolut.money.transfer.dto.AccountDetailRequest;
import com.revolut.money.transfer.dto.GenericResponse;
import com.revolut.money.transfer.dto.TransferRequest;
import com.revolut.money.transfer.service.AccountDetailServiceImpl;
import com.revolut.money.transfer.util.ResourceConstant;
import com.revolut.money.transfer.util.ResponseMessageConstant;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Mockito;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;


public class TransactionResourceTest {

    private static final AccountDetailServiceImpl accountDetailService = Mockito.mock(AccountDetailServiceImpl.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new TransactionResource(accountDetailService))
            .build();

    @Test
    public void testShould_Return_ErrorMessage_When_TransferAmountIsZero() {
        Response response = resources.target(ResourceConstant.TRANSACTION_BASE + ResourceConstant.TRANSFER_REQUEST)
                .request()
                .post(Entity.entity(getTransferRequest(),
                        MediaType.APPLICATION_JSON));
        String jsonResponse = response.readEntity(String.class);
        Assert.assertEquals("Response message should be equal", "amount must be greater than or equal to 1", JsonPath.read(jsonResponse, "$.errors.[0]"));

    }

    @Test
    public void testShould_Return_ErrorMessage_When_FromAccountIsEmptyOrNull() {
        TransferRequest transferRequest = getTransferRequest();
        transferRequest.setFromAccount(null);
        transferRequest.setAmount(BigDecimal.ONE);
        Response response = resources.target(ResourceConstant.TRANSACTION_BASE + ResourceConstant.TRANSFER_REQUEST)
                .request()
                .post(Entity.entity(transferRequest,
                        MediaType.APPLICATION_JSON));
        String jsonResponse = response.readEntity(String.class);
        Assert.assertEquals("Response message should be equal", "fromAccount may not be null", JsonPath.read(jsonResponse, "$.errors.[0]"));

    }

    @Test
    public void testShould_Return_ErrorMessage_When_ToAccountIsEmptyOrNull() {
        TransferRequest transferRequest = getTransferRequest();
        transferRequest.setAmount(BigDecimal.ONE);
        transferRequest.setToAccount(null);
        Response response = resources.target(ResourceConstant.TRANSACTION_BASE + ResourceConstant.TRANSFER_REQUEST)
                .request()
                .post(Entity.entity(transferRequest,
                        MediaType.APPLICATION_JSON));
        String jsonResponse = response.readEntity(String.class);
        Assert.assertEquals("Response message should be equal", "toAccount may not be null", JsonPath.read(jsonResponse, "$.errors.[0]"));
    }

    @Test
    public void testShould_Transfer_Amount_When_ValidRequest() {
        TransferRequest transferRequest = getTransferRequest();
        transferRequest.setAmount(BigDecimal.ONE);
        Mockito.when(accountDetailService.transfer(Mockito.any(TransferRequest.class)))
                .thenReturn(new GenericResponse(Response.Status.OK.getStatusCode(), ResponseMessageConstant.SUCCESSFULLY_TRANSFERRED));
        Response response = resources.target(ResourceConstant.TRANSACTION_BASE + ResourceConstant.TRANSFER_REQUEST)
                .request()
                .post(Entity.entity(transferRequest,
                        MediaType.APPLICATION_JSON));
        Assert.assertEquals("Response code should be equal ", Response.Status.OK.getStatusCode(), response.getStatus());
        String jsonResponse = response.readEntity(String.class);
        Assert.assertEquals("Response code should be equal ", Integer.valueOf(200), JsonPath.read(jsonResponse, "$.code"));
        Assert.assertEquals("Response message should be equal ", ResponseMessageConstant.SUCCESSFULLY_TRANSFERRED, JsonPath.read(jsonResponse, "$.data"));

    }


    private TransferRequest getTransferRequest() {
        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setToAccount(getAccountDetailRequest("Pradip Rimal", "Acc-123"));
        transferRequest.setFromAccount(getAccountDetailRequest("Evan Rc", "Acc-1234"));
        transferRequest.setAmount(BigDecimal.ZERO);
        return transferRequest;
    }

    private AccountDetailRequest getAccountDetailRequest(String name, String accountNumber) {
        AccountDetailRequest accountDetailRequest = new AccountDetailRequest();
        accountDetailRequest.setAccountHolderName(name);
        accountDetailRequest.setAccountNumber(accountNumber);
        return accountDetailRequest;
    }

}
