package model;

import com.sikadali.banking.Clock;
import com.sikadali.banking.Transaction;
import com.sikadali.banking.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class TransactionRepositoryShould {
    public static final String TODAY = "24.12.2015";
    @Mock Clock clock;

    private TransactionRepository transactionRepository;


    @BeforeEach
    public void setUp() {
        transactionRepository = new TransactionRepository(clock);
        given(clock.todayAsString()).willReturn(TODAY);
    }

    @Test
    public void create_and_store_a_deposit_transaction () {
        transactionRepository.addDeposit(100);

        List<Transaction> transactions = transactionRepository.allTransactions();

        assertThat(transactions.size()).isEqualTo(1);
        assertThat(transactions.get(0)).isEqualTo(transaction(TODAY, 100));
    }

    @Test
    public void create_and_store_a_withdrawal_transaction () {
        transactionRepository.addWithdrawal(100);

        List<Transaction> transactions = transactionRepository.allTransactions();

        assertThat(transactions.size()).isEqualTo(1);
        assertThat(transactions.get(0)).isEqualTo(transaction(TODAY, -100));
    }

    private Transaction transaction(String date, int amount) {
        return new Transaction(date, amount);
    }
}
