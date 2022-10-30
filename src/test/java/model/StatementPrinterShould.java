package model;

import com.sikadali.banking.Console;
import com.sikadali.banking.StatementPrinter;
import com.sikadali.banking.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class StatementPrinterShould {

    public static final List<Transaction> NO_TRANSACTIONS = Collections.EMPTY_LIST;
    @Mock Console console;

    private StatementPrinter statementPrinter;

    @BeforeEach
    public void setUp() {
        statementPrinter = new StatementPrinter(console);
    }

    @Test
    public void always_print_the_header(){
        statementPrinter.print(NO_TRANSACTIONS);
        verify(console).printLine("Date | Amount | Balance");
    }

    @Test
    public void print_transactions_in_reverse_chronological_order(){
        List<Transaction> transactions = transactionsContaining(
                deposit("24.12.2015", 500),
                withdrawal("23.8.2016", 100),
                deposit("10.09.2016", 200)
        );

        statementPrinter.print(transactions);

        InOrder inOrder = Mockito.inOrder(console);
        inOrder.verify(console).printLine("Date | Amount | Balance");
        inOrder.verify(console).printLine("10.09.2016 | 200,00 | 600,00");
        inOrder.verify(console).printLine("23.8.2016 | -100,00 | 400,00");
        inOrder.verify(console).printLine("24.12.2015 | 500,00 | 500,00");;
    }

    private List<Transaction> transactionsContaining(Transaction... transactions) {
        return Arrays.asList(transactions);
    }

    private Transaction deposit(String date, int amount) {
        return new Transaction(date, amount);
    }
    private Transaction withdrawal(String date, int amount) {
        return new Transaction(date, -amount);
    }
}
