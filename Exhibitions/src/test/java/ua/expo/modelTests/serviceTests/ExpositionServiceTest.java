package ua.expo.modelTests.serviceTests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.expo.model.dao.impl.JDBCExpositionDao;
import ua.expo.model.entity.ExhibitionHall;
import ua.expo.model.entity.Exposition;
import ua.expo.model.service.ExpositionService;
import ua.expo.model.service.HallsService;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class ExpositionServiceTest {
    @Mock
    private HallsService hallsService;
    @Mock
    private JDBCExpositionDao expoDao;

    private ExpositionService service;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        service = ExpositionService.getInstance();
        service.setExpoDao(expoDao);
        service.setHallsService(hallsService);
    }

    @Test
    public void getExpoListPageHallTest() {
        int page = 5;
        int hallId = 3;
        int postOnPage = service.getPostOnPage();
        int offset = (page - 1) * postOnPage;
        ArgumentCaptor<Integer> offsetArg = ArgumentCaptor.forClass(Integer.class);
        when(expoDao.getInRangeHall(offsetArg.capture(), anyInt(), anyInt())).thenReturn(new ArrayList<>());
        service.getExpoList(page + "", hallId + "");
        Assert.assertEquals(offset, offsetArg.getValue().intValue());
        verify(expoDao, times(1)).getInRangeHall(anyInt(), anyInt(), anyInt());
    }

    @Test
    public void getExpoListPageTest() {
        int page = 5;
        int postOnPage = service.getPostOnPage();
        int offset = (page - 1) * postOnPage;
        ArgumentCaptor<Integer> offsetArg = ArgumentCaptor.forClass(Integer.class);
        when(expoDao.getInRange(offsetArg.capture(), anyInt())).thenReturn(new ArrayList<>());
        service.getExpoList(page + "", null);
        Assert.assertEquals(offset, offsetArg.getValue().intValue());
        verify(expoDao, times(1)).getInRange(anyInt(), anyInt());
    }

    @Test
    public void getExpoListHallTest() {
        int hallId = 3;
        int offset = 0;
        ArgumentCaptor<Integer> offsetArg = ArgumentCaptor.forClass(Integer.class);
        when(expoDao.getInRangeHall(offsetArg.capture(), anyInt(), anyInt())).thenReturn(new ArrayList<>());
        service.getExpoList(null, hallId + "");
        Assert.assertEquals(offset, offsetArg.getValue().intValue());
        verify(expoDao, times(1)).getInRangeHall(anyInt(), anyInt(), anyInt());
    }

    @Test
    public void getExpoListTest() {
        int offset = 0;
        ArgumentCaptor<Integer> offsetArg = ArgumentCaptor.forClass(Integer.class);
        when(expoDao.getInRange(offsetArg.capture(), anyInt())).thenReturn(new ArrayList<>());
        service.getExpoList(null, null);
        Assert.assertEquals(offset, offsetArg.getValue().intValue());
        verify(expoDao, times(1)).getInRange(anyInt(), anyInt());
    }

    @Test
    public void getNumberOfPageHallTest() {
        String hallId = "1";
        int result = 10;
        int postOnPage = service.getPostOnPage();
        int expected = result % postOnPage != 0 ? (result / postOnPage + 1) : (result / postOnPage);
        when(hallsService.getNumberOfRows(anyInt())).thenReturn(result);
        int pages = service.getNumberOfPages(hallId);
        verify(hallsService, times(1)).getNumberOfRows(anyInt());
        verify(hallsService, never()).getNumberOfRows();
        Assert.assertEquals(expected, pages);
    }

    @Test
    public void getNumberOfPageTest() {
        int result = 10;
        int postOnPage = service.getPostOnPage();
        int expected = result % postOnPage != 0 ? (result / postOnPage + 1) : (result / postOnPage);
        when(hallsService.getNumberOfRows()).thenReturn(result);
        int pages = service.getNumberOfPages(null);
        verify(hallsService, never()).getNumberOfRows(anyInt());
        verify(hallsService, times(1)).getNumberOfRows();
        Assert.assertEquals(expected, pages);
    }

    @Test
    public void addTest() {
        String test = "test";
        ArgumentCaptor<Exposition> arg = ArgumentCaptor.forClass(Exposition.class);
        doNothing().when(expoDao).insert(arg.capture());
        service.add(test, test, null, "1", "1999-01-01", "1999-01-02", "1");
        verify(expoDao, times(1)).insert(any(Exposition.class));
        verify(hallsService, times(1)).updateList(anyInt());
        Assert.assertEquals(arg.getValue().getTheme(), test);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addWrongDateTest() {
        String test = "test";
        doNothing().when(expoDao).insert(any(Exposition.class));
        service.add(test, test, null, "1", "1999-02-01", "1999-01-02", "1");
        verify(expoDao, times(0)).insert(any(Exposition.class));
        verify(hallsService, times(0)).updateList(anyInt());
    }

    @Test
    public void removeTest() {
        String hallId = 1 + "";
        when(expoDao.getById(anyInt()))
                .thenReturn(new Exposition.Builder().setHall(new ExhibitionHall.Builder().setId(1).build()).build());
        service.delete(hallId);
        verify(expoDao, times(1)).saveDelete(any(Exposition.class));
        verify(expoDao, never()).delete(any(Exposition.class));
    }

    @Test
    public void updateTest(){
        String test = "test";
        service.update("1", test, test, null
                , "1", "1999-01-01", "1999-01-01","1");
        verify(expoDao, times(1)).update(any(Exposition.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateWrongDateTest(){
        String test = "test";
        service.update("1", test, test, null
                , "1", "2000-01-01", "1999-01-01","1");
        verify(expoDao, never()).update(any(Exposition.class));
    }
}
