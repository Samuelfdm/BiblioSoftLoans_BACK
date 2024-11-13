package cvds.ProyectoPrestamos.controller;

import cvds.ProyectoPrestamos.model.History;
import cvds.ProyectoPrestamos.model.Loans;
import cvds.ProyectoPrestamos.model.LoanState;
import cvds.ProyectoPrestamos.service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class LoansControllerTest {

    @Mock
    private LoanService loanService;

    @InjectMocks
    private loansController loansController;

    private Loans loan;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        loan = new Loans("123", "Juan Pérez", LocalDate.now().plusDays(14), LoanState.Prestado, "B001");
    }

    @Test
    public void testCreateLoan() {
        when(loanService.createLoan(anyString(), anyString())).thenReturn(loan);

        Loans createdLoan = loansController.createLoan(loan);

        assertNotNull(createdLoan);
        assertEquals("B001", createdLoan.getBookCode());
        assertEquals("123", createdLoan.getStudientId());
        verify(loanService, times(1)).createLoan(anyString(), anyString());
    }

    @Test
    public void testDeleteLoan() {
        Long loanId = 1L;
        when(loanService.deleteLoan(loanId)).thenReturn(Optional.of(loan));

        Optional<Loans> deletedLoan = loansController.deleteLoan(loanId);

        assertTrue(deletedLoan.isPresent());
        assertEquals("B001", deletedLoan.get().getBookCode());
        verify(loanService, times(1)).deleteLoan(loanId);
    }

    @Test
    public void testShowAvailability() {
        String bookCode = "B001";
        when(loanService.showAvailability(bookCode)).thenReturn("Available");

        String availability = loansController.showAvailability(bookCode);

        assertEquals("Available", availability);
        verify(loanService, times(1)).showAvailability(bookCode);
    }

    @Test
    public void testReturnBook() {
        loan.setLoanState(LoanState.Devuelto);

        doNothing().when(loanService).returnBook(any(), anyString(), anyString());

        loansController.returnBook(loan);

        verify(loanService, times(1)).returnBook(any(), anyString(), anyString());
    }

    @Test
    public void testShowAllLoans() {
        when(loanService.showAllloans()).thenReturn(List.of(loan));

        List<Loans> loans = loansController.showAllloans();

        assertNotNull(loans);
        assertFalse(loans.isEmpty());
        assertEquals(1, loans.size());
        verify(loanService, times(1)).showAllloans();
    }

    @Test
    public void testShowHistory() {
        History history = new History("B001", LocalDate.now(), LocalDate.now().plusDays(14), LoanState.Prestado, "Juan Pérez");
        when(loanService.showHistory()).thenReturn(List.of(history));

        List<History> historyList = loansController.showHistory();

        assertNotNull(historyList);
        assertFalse(historyList.isEmpty());
        assertEquals(1, historyList.size());
        verify(loanService, times(1)).showHistory();
    }
}
