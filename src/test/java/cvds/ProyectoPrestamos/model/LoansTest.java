package cvds.ProyectoPrestamos.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class LoansTest {

    private Loans loan;

    @BeforeEach
    public void setUp() {
        loan = new Loans("123", "Juan Pérez", LocalDate.now().plusDays(14), LoanState.Prestado, "B001");
    }

    @Test
    public void testLoanConstructor() {
        assertNotNull(loan);
        assertEquals("123", loan.getStudientId());
        assertEquals("Juan Pérez", loan.getStudientName());
        assertEquals(LoanState.Prestado, loan.getLoanState());
        assertEquals("B001", loan.getBookCode());
        assertNotNull(loan.getLoandDate());
        assertEquals(LocalDate.now().plusDays(14), loan.getReturnDate());
    }

    @Test
    public void testSettersAndGetters() {
        loan.setBookCode("B002");
        loan.setStudientId("456");
        loan.setStudientName("Ana López");
        loan.setLoanState(LoanState.Devuelto);
        loan.setReturnDate(LocalDate.now().plusDays(7));
        loan.setLoandDate(LocalDate.now().minusDays(5));

        assertEquals("B002", loan.getBookCode());
        assertEquals("456", loan.getStudientId());
        assertEquals("Ana López", loan.getStudientName());
        assertEquals(LoanState.Devuelto, loan.getLoanState());
        assertEquals(LocalDate.now().plusDays(7), loan.getReturnDate());
        assertEquals(LocalDate.now().minusDays(5), loan.getLoandDate());
    }

    @Test
    public void testDefaultLoanDate() {
        assertNotNull(loan.getLoandDate());
        assertTrue(loan.getLoandDate().isEqual(LocalDate.now()) || loan.getLoandDate().isBefore(LocalDate.now()));
    }

    @Test
    public void testLoanStateTransition() {
        loan.setLoanState(LoanState.Prestado);
        assertEquals(LoanState.Prestado, loan.getLoanState());

        loan.setLoanState(LoanState.Devuelto);
        assertEquals(LoanState.Devuelto, loan.getLoanState());
    }

    @Test
    public void testReturnDate() {
        LocalDate returnDate = LocalDate.now().plusDays(14);
        loan.setReturnDate(returnDate);
        assertEquals(returnDate, loan.getReturnDate());
    }

    @Test
    public void testLoanID() {
        loan.setID(100L);
        assertEquals(100L, loan.getID());
    }

    @Test
    public void testLoanStateEnum() {
        loan.setLoanState(LoanState.Vencido);
        assertEquals(LoanState.Vencido, loan.getLoanState());
    }

    @Test
    public void testLoanWithNullStudentId() {
        loan.setStudientId(null);
        assertNull(loan.getStudientId());
    }

    @Test
    public void testLoanWithEmptyBookCode() {
        loan.setBookCode("");
        assertEquals("", loan.getBookCode());
    }

    @Test
    public void testLoanWithFutureReturnDate() {
        LocalDate futureReturnDate = LocalDate.now().plusDays(30);
        loan.setReturnDate(futureReturnDate);
        assertEquals(futureReturnDate, loan.getReturnDate());
    }

    @Test
    public void testLoanWithPastReturnDate() {
        LocalDate pastReturnDate = LocalDate.now().minusDays(1);
        loan.setReturnDate(pastReturnDate);
        assertEquals(pastReturnDate, loan.getReturnDate());
    }


    @Test
    public void testLoanWithNullReturnDate() {
        loan.setReturnDate(null);
        assertNull(loan.getReturnDate());
    }

    @Test
    public void testLoanStateEquals() {
        LoanState state1 = LoanState.Prestado;
        LoanState state2 = LoanState.Prestado;
        LoanState state3 = LoanState.Devuelto;

        assertTrue(state1.equals(state2));
        assertFalse(state1.equals(state3));
    }

    @Test
    public void testLoanStateHashCode() {
        LoanState state1 = LoanState.Prestado;
        LoanState state2 = LoanState.Prestado;

        assertEquals(state1.hashCode(), state2.hashCode());
    }

    @Test
    public void testDifferentLoanStates() {
        loan.setLoanState(LoanState.Devuelto);
        assertEquals(LoanState.Devuelto, loan.getLoanState());

        loan.setLoanState(LoanState.Vencido);
        assertEquals(LoanState.Vencido, loan.getLoanState());
    }

    @Test
    public void testNullStudentName() {
        loan.setStudientName(null);
        assertNull(loan.getStudientName());
    }

    @Test
    public void testCreateLoanWithCurrentDate() {
        Loans newLoan = new Loans("789", "Pedro Gómez", LocalDate.now().plusDays(10), LoanState.Prestado, "B003");
        assertTrue(newLoan.getLoandDate().isEqual(LocalDate.now()));
    }

    @Test
    public void testReturnDateAfterLoanDate() {
        loan.setReturnDate(LocalDate.now().plusDays(14));
        assertTrue(loan.getReturnDate().isAfter(loan.getLoandDate()));
    }
}
