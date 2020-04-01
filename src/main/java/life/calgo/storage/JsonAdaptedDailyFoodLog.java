package life.calgo.storage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import life.calgo.commons.exceptions.IllegalValueException;
import life.calgo.model.day.DailyFoodLog;
import life.calgo.model.food.Food;

/**
 * Jackson-friendly version of {@link DailyFoodLog}.
 */
public class JsonAdaptedDailyFoodLog {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "DailyFoodLog's %s field is missing!";

    private final LinkedHashMap<JsonAdaptedFood, Double> foods;
    private final LinkedHashMap<JsonAdaptedFood, ArrayList<Integer>> ratings;
    private final LocalDate localDate;

    /**
     * Constructs a {@code JsonDailyFoodLog} with the given details.
     */
    @JsonCreator
    public JsonAdaptedDailyFoodLog(
        @JsonProperty("foods") LinkedHashMap<JsonAdaptedFood, Double> foods,
        @JsonProperty("ratings") LinkedHashMap<JsonAdaptedFood, ArrayList<Integer>> ratings,
        @JsonProperty("localDate") LocalDate localDate
    ) {
        this.foods = foods;
        this.ratings = ratings;
        this.localDate = localDate;
    }

    /**
     * Converts a given {@code JsonDailyFoodLog} into this class for Jackson use.
     */
    public JsonAdaptedDailyFoodLog(DailyFoodLog source) {
        foods = adaptFoods(source.copyFoods());
        ratings = adaptRatings(source.copyRatings());
        localDate = source.getLocalDate();
    }

    /**
     * Helper method to convert Food into adaptedFood.
     */
    private LinkedHashMap<JsonAdaptedFood, Double> adaptFoods(LinkedHashMap<Food, Double> foods) {
        LinkedHashMap<JsonAdaptedFood, Double> adaptedFoods = new LinkedHashMap<>();
        for (Food food : foods.keySet()) {
            adaptedFoods.put(new JsonAdaptedFood(food), foods.get(food));
        }
        return adaptedFoods;
    }

    /**
     * Helper method to convert Food into adaptedFood.
     */
    private LinkedHashMap<JsonAdaptedFood, ArrayList<Integer>> adaptRatings(
            LinkedHashMap<Food, ArrayList<Integer>> ratings) {
        LinkedHashMap<JsonAdaptedFood, ArrayList<Integer>> adaptedRatings = new LinkedHashMap<>();
        for (Food food : ratings.keySet()) {
            adaptedRatings.put(new JsonAdaptedFood(food), new ArrayList<>(ratings.get(food)));
        }
        return adaptedRatings;
    }

    /**
     * Helper method to convert adaptedFood into food.
     */
    private LinkedHashMap<Food, Double> unAdaptFoods(LinkedHashMap<JsonAdaptedFood, Double> adaptedFoods)
            throws IllegalValueException {
        LinkedHashMap<Food, Double> foods = new LinkedHashMap<>();
        for (JsonAdaptedFood adaptedFood : adaptedFoods.keySet()) {
            foods.put(adaptedFood.toModelType(), adaptedFoods.get(adaptedFood));
        }
        return foods;
    }

    /**
     * Helper method to convert adaptedFood into food.
     */
    private LinkedHashMap<Food, ArrayList<Integer>> unAdaptRatings(
            LinkedHashMap<JsonAdaptedFood, ArrayList<Integer>> adaptedRatings)
            throws IllegalValueException {
        LinkedHashMap<Food, ArrayList<Integer>> ratings = new LinkedHashMap<>();
        for (JsonAdaptedFood adaptedFood : adaptedRatings.keySet()) {
            ratings.put(adaptedFood.toModelType(), new ArrayList<>(adaptedRatings.get(adaptedFood)));
        }
        return ratings;
    }

    /**
     * Converts this Jackson-friendly adapted DailyFoodLog object into the model's {@code DailyFoodLog} object.
     * @return a DailyFoodLog equivalent of this adaptedDailyFoodLog object.
     * @throws IllegalValueException if there were any data constraints violated in the adaptedDailyFoodLog.
     */
    public DailyFoodLog toModelType() throws IllegalValueException {
        return new DailyFoodLog(unAdaptFoods(foods), unAdaptRatings(ratings), localDate);
    }
}
