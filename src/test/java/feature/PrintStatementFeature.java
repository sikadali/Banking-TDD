package feature;

import com.sikadali.banking.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
public class PrintStatementFeature {
    @Mock Console console;
    @Mock Clock clock;

    private Account account;


    @BeforeEach
    public void setUp() {
        TransactionRepository transactionRepository = new TransactionRepository(clock);
        StatementPrinter statementPrinter = new StatementPrinter(console);
        account = new Account(transactionRepository, statementPrinter);
    }

    @Test
    public void print_statement_containing_all_transactions(){
        given(clock.todayAsString()).willReturn("24.12.2015", "23.8.2016", "10.09.2016");

        account.deposit(500);
        account.withdraw(100);
        account.deposit(200);
        account.printStatement();

        InOrder inOrder = Mockito.inOrder(console);
        inOrder.verify(console).printLine("Date | Amount | Balance");
        inOrder.verify(console).printLine("10.09.2016 | 200,00 | 600,00");
        inOrder.verify(console).printLine("23.8.2016 | -100,00 | 400,00");
        inOrder.verify(console).printLine("24.12.2015 | 500,00 | 500,00");

    }
}
