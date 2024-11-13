package cvds.ProyectoPrestamos.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class HistoryTest {

    private History history;

    @BeforeEach
    public void setUp() {
        history = new History("B001", LocalDate.now(), LocalDate.now().plusDays(14), LoanState.Prestado, "Juan Pérez");
    }

    @Test
    public void testHistoryConstructorWithAllFields() {
        assertNotNull(history);
        assertEquals("B001", history.getBookCode());
        assertEquals(LoanState.Prestado, history.getState());
        assertEquals("Juan Pérez", history.getStudientName());
        assertNotNull(history.getLoanDate());
        assertNotNull(history.getReturnDate());
    }

    @Test
    public void testHistoryConstructorWithRequiredFields() {
        History historyWithoutReturnDate = new History("B002", LoanState.Devuelto, "Ana López");
        assertNotNull(historyWithoutReturnDate);
        assertEquals("B002", historyWithoutReturnDate.getBookCode());
        assertEquals(LoanState.Devuelto, historyWithoutReturnDate.getState());
        assertEquals("Ana López", historyWithoutReturnDate.getStudientName());
    }

    @Test
    public void testSettersAndGetters() {
        history.setBookCode("B002");
        history.setLoanDate(LocalDate.now().minusDays(7));
        history.setReturnDate(LocalDate.now().plusDays(7));
        history.setState(LoanState.Prestado);
        history.setStudientName("Carlos García");

        assertEquals("B002", history.getBookCode());
        assertEquals(LocalDate.now().minusDays(7), history.getLoanDate());
        assertEquals(LocalDate.now().plusDays(7), history.getReturnDate());
        assertEquals(LoanState.Prestado, history.getState());
        assertEquals("Carlos García", history.getStudientName());
    }

    @Test
    public void testLoanDateBeforeReturnDate() {
        LocalDate loanDate = LocalDate.now();
        LocalDate returnDate = LocalDate.now().plusDays(14);

        history.setLoanDate(loanDate);
        history.setReturnDate(returnDate);

        assertTrue(history.getLoanDate().isBefore(history.getReturnDate()));
    }

    @Test
    public void testInvalidReturnDate() {
        LocalDate loanDate = LocalDate.now();
        LocalDate returnDate = LocalDate.now().minusDays(1);

        history.setLoanDate(loanDate);
        history.setReturnDate(returnDate);

        assertFalse(history.getLoanDate().isBefore(history.getReturnDate()));
    }




    @Test
    public void testHistoryWithFutureLoanDate() {
        LocalDate futureLoanDate = LocalDate.now().plusDays(10);
        LocalDate returnDate = futureLoanDate.plusDays(14);
        History futureHistory = new History("B003", futureLoanDate, returnDate, LoanState.Prestado, "Lucía Martín");

        assertEquals(futureLoanDate, futureHistory.getLoanDate());
        assertEquals(returnDate, futureHistory.getReturnDate());
        assertEquals("Lucía Martín", futureHistory.getStudientName());
    }

    @Test
    public void testHistoryWithPastLoanDate() {
        LocalDate pastLoanDate = LocalDate.now().minusDays(10);
        LocalDate returnDate = pastLoanDate.plusDays(14);
        History pastHistory = new History("B004", pastLoanDate, returnDate, LoanState.Devuelto, "Carlos Pérez");

        assertEquals(pastLoanDate, pastHistory.getLoanDate());
        assertEquals(returnDate, pastHistory.getReturnDate());
        assertEquals("Carlos Pérez", pastHistory.getStudientName());
    }

    @Test
    public void testNullLoanDate() {
        History nullLoanDateHistory = new History("B005", null, LocalDate.now().plusDays(14), LoanState.Prestado, "María Gómez");
        assertNull(nullLoanDateHistory.getLoanDate());
    }

    @Test
    public void testNullReturnDate() {
        History nullReturnDateHistory = new History("B006", LocalDate.now(), null, LoanState.Prestado, "José García");
        assertNull(nullReturnDateHistory.getReturnDate());
    }
}
