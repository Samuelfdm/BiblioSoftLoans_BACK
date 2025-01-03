package edu.eci.cvds.BiblioSoftLoans.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class LoanHistoryTest {

    private LoanHistory loanHistory;
    private Loan loan;

    @BeforeEach
    void setUp() {
        loan = new Loan(
                "673bb7ad521c2f08810d4f7c",
                "Maria Perez",
                "COPY123",
                "BOOK000",
                "libro de la selva",
                LocalDate.now(),
                LocalDate.now().plusDays(7),
                LoanState.Loaned
        );
        loanHistory = new LoanHistory(LocalDate.now(), "BUENO");
        loanHistory.setLoan(loan);
    }

    @Test
    void testLoanHistoryCreation() {
        assertEquals(LocalDate.now(), loanHistory.getRecordDate());
        assertEquals("BUENO", loanHistory.getCopyState());
        assertEquals(loan, loanHistory.getLoan());
    }

    @Test
    void testSetCopyState() {
        loanHistory.setCopyState("Damaged");
        assertEquals("Damaged", loanHistory.getCopyState());
    }

    @Test
    void testSetDate() {
        LocalDate newDate = LocalDate.now().minusDays(3);
        loanHistory.setRecordDate(newDate);
        assertEquals(newDate, loanHistory.getRecordDate());
    }

    @Test
    void testSetLoan() {
        Loan newLoan = new Loan(
                "673bb7ad521c2f08810d4f7c",
                "Maria Perez",
                "COPY789",
                "BOOK001",
                "libro de la selva",
                LocalDate.now(),
                LocalDate.now().plusDays(10),
                LoanState.Loaned
        );
        loanHistory.setLoan(newLoan);
        assertEquals(newLoan, loanHistory.getLoan());
    }

    @Test
    void testDefaultConstructor() {
        LoanHistory emptyLoanHistory = new LoanHistory();
        assertNull(emptyLoanHistory.getRecordDate());
        assertNull(emptyLoanHistory.getCopyState());
        assertNull(emptyLoanHistory.getLoan());
    }

    @Test
    void testLoanHistoryDateNotNull() {
        assertNotNull(loanHistory.getRecordDate());
    }

    @Test
    void testLoanHistoryCopyStateNotNull() {
        assertNotNull(loanHistory.getCopyState());
    }

    @Test
    void testLoanHistoryLoanNotNullAfterSet() {
        Loan newLoan = new Loan(
                "673bb7ad521c2f08810d4f7c",
                "Maria Perez",
                "COPY999",
                "BOOK002",
                "libro de la selva",
                LocalDate.now(),
                LocalDate.now().plusDays(5),
                LoanState.Loaned
        );
        loanHistory.setLoan(newLoan);
        assertNotNull(loanHistory.getLoan());
    }

    @Test
    void testDateInPast() {
        loanHistory.setRecordDate(LocalDate.now().minusDays(30));
        assertTrue(loanHistory.getRecordDate().isBefore(LocalDate.now()));
    }

    @Test
    void testDateInFuture() {
        loanHistory.setRecordDate(LocalDate.now().plusDays(15));
        assertTrue(loanHistory.getRecordDate().isAfter(LocalDate.now()));
    }

    @Test
    void testDateConsistencyOnMultipleChanges() {
        LocalDate newDate1 = LocalDate.now().minusDays(2);
        LocalDate newDate2 = LocalDate.now().plusDays(5);
        loanHistory.setRecordDate(newDate1);
        assertEquals(newDate1, loanHistory.getRecordDate());

        loanHistory.setRecordDate(newDate2);
        assertEquals(newDate2, loanHistory.getRecordDate());
    }

    @Test
    void testLoanAssociationIntegrity() {
        Loan anotherLoan = new Loan(
                "673bb7ad521c2f08810d4f7c",
                "Maria Perez",
                "COPY555",
                "BOOK003",
                "libro de la selva",
                LocalDate.now(),
                LocalDate.now().plusDays(7),
                LoanState.Loaned
        );
        loanHistory.setLoan(anotherLoan);
        assertEquals(anotherLoan, loanHistory.getLoan());
    }
}
