package com.revolut.money.transfer.resource;

import com.revolut.money.transfer.domain.AccountDetail;
import com.revolut.money.transfer.dto.TransferRequest;
import com.revolut.money.transfer.service.AccountDetailService;
import com.revolut.money.transfer.util.ResourceConstant;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path(ResourceConstant.TRANSACTION_BASE)
@Produces(MediaType.APPLICATION_JSON)
public class TransactionResource {

    private final AccountDetailService accountDetailService;

    public TransactionResource(AccountDetailService accountDetailService) {
        this.accountDetailService = accountDetailService;
    }

    @POST
    @Path(ResourceConstant.TRANSFER_REQUEST)
    public Response createPart(@Valid TransferRequest transferRequest) {
        return Response.status(Response.Status.OK).entity(accountDetailService.transfer(transferRequest)).build();
    }

    @GET
    public List<AccountDetail> getALl() {
        System.out.println("called");
        List<AccountDetail> all = accountDetailService.getAll();
        return all;
    }

    @GET
    @Path("/test")
    public List<AccountDetail> save() {
        accountDetailService.save();
        return accountDetailService.getAll();
    }

}
