package cvds.ProyectoPrestamos.service;

import static cvds.ProyectoPrestamos.model.LoanState.Prestado;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import cvds.ProyectoPrestamos.Exceptions.BookApiException;
import cvds.ProyectoPrestamos.Exceptions.BookLoanException;
import cvds.ProyectoPrestamos.model.History;
import cvds.ProyectoPrestamos.model.Loans;
import cvds.ProyectoPrestamos.model.LoanState;
import cvds.ProyectoPrestamos.repository.loansRepository;
import cvds.ProyectoPrestamos.repository.historyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class LoanServiceTest {

    @Mock
    private loansRepository repository;

    @Mock
    private historyRepository historyRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private LoanService loanService;

    private Loans sampleLoan;

    @Mock
    private loansRepository loansRepository;



    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleLoan = new Loans("123", "Juan Pérez", LocalDate.now().plusDays(14), Prestado, "B001");
    }

    @Test
    public void testDeleteLoan() {
        Long loanId = 1L;
        when(repository.findById(loanId)).thenReturn(Optional.of(sampleLoan));
        Optional<Loans> deletedLoan = loanService.deleteLoan(loanId);
        assertTrue(deletedLoan.isPresent());
        verify(repository, times(1)).deleteById(loanId);
    }


    @Test
    public void testCreateLoanAlreadyBorrowed() {
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn("{\"state\": \"Prestado\", \"category\": \"LITERATURA\"}");
        when(repository.findByBookCodeAndStudientId(anyString(), anyString())).thenReturn(Optional.empty());
        assertThrows(BookLoanException.class, () -> {
            loanService.createLoan("B001", "123");
        });
    }



    @Test
    public void testReturnBookNoLoanFound() {
        when(repository.findByBookCodeAndStudientId(anyString(), anyString())).thenReturn(Optional.empty());
        assertThrows(BookLoanException.class, () -> {
            loanService.returnBook(LoanState.Devuelto, "B001", "123");
        });
    }

    @Test
    public void testGenerateReturnDateForLiteratureBook() {
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn("{\"category\": \"LITERATURA\"}");
        LocalDate returnDate = loanService.generateReturnDate("B001");
        assertEquals(LocalDate.now().plusDays(14), returnDate);
    }

    @Test
    public void testGenerateReturnDateForInvalidCategory() {
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn("{\"category\": \"INVALID\"}");
        assertThrows(BookApiException.class, () -> {
            loanService.generateReturnDate("B001");
        });
    }

    @Test
    public void testUpdateHistory() {
        History sampleHistory = new History("B001", LocalDate.now(), LocalDate.now().plusDays(14), Prestado, "Juan Pérez");
        when(historyRepository.save(any(History.class))).thenReturn(sampleHistory);
        loanService.updateHistory("B001", LocalDate.now(), LocalDate.now().plusDays(14), Prestado, "Juan Pérez");
        verify(historyRepository, times(1)).save(any(History.class));
    }


    @Test
    public void testGenerateReturnDateInvalidCategory() {
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn("{\"category\": \"INVALID\"}");
        assertThrows(BookApiException.class, () -> {
            loanService.generateReturnDate("B001");
        });
    }

    @Test
    public void testUpdateHistorySuccessfully() {
        History sampleHistory = new History("B001", LocalDate.now(), LocalDate.now().plusDays(14), Prestado, "Juan Pérez");
        when(historyRepository.save(any(History.class))).thenReturn(sampleHistory);
        loanService.updateHistory("B001", LocalDate.now(), LocalDate.now().plusDays(14), Prestado, "Juan Pérez");
        verify(historyRepository, times(1)).save(any(History.class));
    }

    @Test
    public void testDeletLoan() {
        Long loanId = 1L;
        when(repository.findById(loanId)).thenReturn(Optional.of(sampleLoan));
        Optional<Loans> deletedLoan = loanService.deleteLoan(loanId);
        assertTrue(deletedLoan.isPresent());
        verify(repository, times(1)).deleteById(loanId);
    }

    @Test
    public void testShowHistory() {
        History history1 = new History("B001", LocalDate.now(), LocalDate.now().plusDays(7), Prestado, "John Doe");
        History history2 = new History("B002", LocalDate.now(), LocalDate.now().plusDays(14), Prestado, "Jane Doe");
        List<History> mockHistory = Arrays.asList(history1, history2);
        when(historyRepository.findAll()).thenReturn(mockHistory);
        List<History> history = loanService.showHistory();
        assertNotNull(history);
        assertEquals(2, history.size());
        assertEquals("John Doe", history.get(0).getStudientName());
    }


    @Test
    public void testUpdateHistorybeforeRetrun() {
        loanService.updateHistory("B001", LocalDate.now(), LocalDate.now().plusDays(7), Prestado, "John Doe");
        verify(historyRepository).save(any(History.class));
    }

    @Test
    public void testGenerateReturnDate() {
        String bookCode = "B001";
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn("{\"category\": \"LITERATURA\"}");
        LocalDate returnDate = loanService.generateReturnDate(bookCode);
        assertEquals(LocalDate.now().plusDays(14), returnDate);
    }

}
