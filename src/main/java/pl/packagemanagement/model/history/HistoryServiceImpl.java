package pl.packagemanagement.model.history;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.packagemanagement.model.pack.Package;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository historyRepository;

    @Override
    public History save(History history) {
        return historyRepository.save(history);
    }
}
