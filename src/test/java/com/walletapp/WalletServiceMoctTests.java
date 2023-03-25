package com.walletapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
@SpringBootTest
public class WalletServiceMoctTests {

    @Autowired
    private WalletService walletService;

    @MockBean
    private WalletRepository walletRepository;

    @Test
    public void testServiceWithOutActualRepository() throws  WalletException {
        given(this.walletRepository.getWalletById(100))
                .willReturn(new WalletDto(100,"Ford", 2500.0));
        assertEquals("Ford",walletService.getWalletById(100).getName());
    }

    @Test
    public void testGetWalletThrowsExceptionTest() throws WalletException{

        given(this.walletRepository.getWalletById(200))
                .willReturn(null);
        assertThrows(WalletException.class,()->walletService.getWalletById(200));
    }

    @Test
    public void addFundsToWalletTest() throws WalletException{

        given(this.walletRepository.getWalletById(200))
                .willReturn(new WalletDto(200,"Ford 1",2000.0));
        given(this.walletRepository.getWalletById(400))
                .willReturn(new WalletDto(400,"Ford 2",4000.0));

        Double newBalance=this.walletService.addFundsToWalletById(200,250.0);
        assertEquals(2250.0,newBalance);
    }

    @Test
    public void withdrawFundsFromWalletTest() throws WalletException{

        given(this.walletRepository.getWalletById(200))
                .willReturn(new WalletDto(200,"Ford 1",2000.0));
        given(this.walletRepository.getWalletById(400))
                .willReturn(new WalletDto(400,"Ford 2",4000.0));

        Double newBalance=this.walletService.withdrawFundsFromWalletById(200,250.0);
        assertEquals(1750.0,newBalance);
    }
    @Test
    public void withdrawFundsFromWalletInsufficientFundExceptionTest() throws WalletException{

        given(this.walletRepository.getWalletById(200))
                .willReturn(new WalletDto(200,"Ford 1",2000.0));
        given(this.walletRepository.getWalletById(400))
                .willReturn(new WalletDto(400,"Ford 2",4000.0));


        assertThrows(WalletException.class,()->this.walletService.withdrawFundsFromWalletById(200,2500.0));
    }
    @Test
    public void withdrawFundsFromWalletInsufficientFundExceptionMessageTest() throws WalletException{

        given(this.walletRepository.getWalletById(200))
                .willReturn(new WalletDto(200,"Ford 1",2020.0));
        given(this.walletRepository.getWalletById(400))
                .willReturn(new WalletDto(400,"Ford 2",4000.0));
        String eMessage="";
        try{
            this.walletService.withdrawFundsFromWalletById(200,2500.0);
        }
        catch (WalletException e){
            eMessage=e.getMessage();
        }
        assertEquals("Insufficient balance, current balance:2020.0",eMessage);
    }

}
