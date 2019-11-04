package pl.packagemanagement.model.history;


import org.springframework.stereotype.Service;
import pl.packagemanagement.model.pack.Package;


public interface HistoryService {
    History save(History history);
}
