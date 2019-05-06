package com.goldfinger.auditability.services;

import com.goldfinger.auditability.models.Export;
import com.goldfinger.auditability.models.SearchFilter;
import com.goldfinger.auditability.repositories.contracts.AuditabilityRepository;
import com.goldfinger.auditability.services.helpers.Parser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(MockitoJUnitRunner.Silent.class)
public class AuditabilityServiceTests {

    @Mock
    private AuditabilityRepository repository;

    @Mock
    private Parser serviceParser;

    @Mock
    private com.goldfinger.auditability.repositories.helpers.Parser repositoryParser;

    @InjectMocks
    private AuditabilityServiceImpl service;

    @Test
    public void addLogs_Should_Return_Id_When_Method_Is_Called() {
        HashMap<String, String> logMap = new HashMap<>();
        String log = "";
        long id = 1;

        Mockito.when(repository.addNewLog(log)).thenReturn(id);

        service.addLog(logMap);
    }

    @Test
    public void addLogs_Should_Return_True_When_Method_Is_Called(){
        HashMap<String, String> logMap = new HashMap<>();
        long id = 1;

        Mockito.when(repository.addKeyValuePair(id, "key", "value")).thenReturn(true);

        service.addLog(logMap);
    }

    @Test(expected = IllegalArgumentException.class)
    public void exportLogsToCSV_Should_Throw_IllegalArgument_Exceptio_When_Method_Is_Called(){
        Export export = new Export();
        export.setSearchFilter(new SearchFilter());

        Mockito.when(serviceParser.logsToCSV(service.getLogs(export.getSearchFilter()), export.getColumnsToExport())).thenThrow(new IllegalArgumentException());

        service.exportLogsToCSV(export);
    }

    @Test
    public void exportLogsToCSV_Should_Return_CSV_When_Method_Is_Called(){
        Export export = new Export();
        export.setSearchFilter(new SearchFilter());
        export.setColumnsToExport(new String[1]);

        String csv = "csv";

        Mockito.when(serviceParser.logsToCSV(service.getLogs(export.getSearchFilter()), export.getColumnsToExport())).thenReturn(csv);

        service.exportLogsToCSV(export);
    }

    @Test
    public void getLogs_Should_Return_All_Searching_Logs_When_Search_Phrase(){
        SearchFilter searchFilter = new SearchFilter();
        searchFilter.setSearch("\"search\"");
        List<Map<String, String>> result = new ArrayList<>();

        List<Integer> ints = repository.searchPhrase(searchFilter.getSearch(), searchFilter.getFilter());
        for(Integer i : ints ){
            result.add(repository.getLog(i));
        }

        Assert.assertEquals(result, service.getLogs(searchFilter));
    }

    @Test
    public void getLogs_Should_Return_All_Searching_Logs_When_Search_Word_In_Pair(){
        SearchFilter searchFilter = new SearchFilter();
        searchFilter.setSearch("username : Rosen");
        List<Map<String, String>> result = new ArrayList<>();
        String[] search = searchFilter.getSearch().split(":", 2);
        List<Integer> ints = repository.searchWordInPair(search[0].trim(), search[1].trim(), searchFilter.getFilter());
        for(Integer i : ints ){
            result.add(repository.getLog(i));
        }

        Assert.assertEquals(result, service.getLogs(searchFilter));
    }

    @Test
    public void getLogs_Should_Return_All_Searching_Logs_When_Search_With_Equal(){
        SearchFilter searchFilter = new SearchFilter();
        searchFilter.setSearch("username = Rosen");
        List<Map<String, String>> result = new ArrayList<>();
        String[] search = searchFilter.getSearch().split("=");
        List<Integer> ints = repository.searchExactTextInPairs(search[0].trim(), search[1].trim(), searchFilter.getFilter());
        for(Integer i : ints ){
            result.add(repository.getLog(i));
        }

        Assert.assertEquals(result, service.getLogs(searchFilter));
    }

    @Test
    public void getLogs_Should_Return_All_Searching_Logs_When_Search_With_FullText_Search(){
        SearchFilter searchFilter = new SearchFilter();
        searchFilter.setSearch("username Rosen admin");
        List<Map<String, String>> result = new ArrayList<>();
        String[] search = searchFilter.getSearch().split(" ");
        List<Integer> ints = repository.fullTextSearch(searchFilter.getSearch());
        for(Integer i : ints ){
            result.add(repository.getLog(i));
        }

        Assert.assertEquals(result, service.getLogs(searchFilter));
    }
}
