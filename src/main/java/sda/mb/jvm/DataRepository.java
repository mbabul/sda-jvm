package sda.mb.jvm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * To jest przykład, dzięki któemu będziemy mogli zasymulować wyciek pamięci, dlatego jest to singleton,
 * nie wzoruj się na tym w swoich projektach
 */

public class DataRepository {

    private List<DataModel> dataModelList = new ArrayList<>();

    private DataRepository() {}

    static DataRepository getInstance() {
        return DataRepository.RepositoryHolder.INSTANCE;
    }

    private static class RepositoryHolder {
        private static final DataRepository INSTANCE = new DataRepository();
    }

    List<DataModel> getAll() {
        return dataModelList;
    }

    Optional<DataModel> get(int id) {
        return dataModelList.stream()
                .filter(e -> e.getId() == id)
                .findAny();
    }

    void save(DataModel e) {
        dataModelList.add(e);
    }

    void deleteAll() {
        dataModelList.clear();
    }

    void delete(int id) {
        dataModelList = dataModelList.stream()
                .filter(e -> e.getId() != id)
                .collect(Collectors.toList());
    }
}
