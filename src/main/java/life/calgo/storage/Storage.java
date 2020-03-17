package life.calgo.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import life.calgo.model.ReadOnlyUserPrefs;
import life.calgo.model.UserPrefs;
import life.calgo.commons.exceptions.DataConversionException;
import life.calgo.model.ReadOnlyFoodRecord;

/**
 * API of the Storage component
 */
public interface Storage extends FoodRecordStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getFoodRecordFilePath();

    @Override
    Optional<ReadOnlyFoodRecord> readFoodRecord() throws DataConversionException, IOException;

    @Override
    void saveFoodRecord(ReadOnlyFoodRecord foodRecord) throws IOException;

}