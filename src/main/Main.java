package main;

import checker.Checkstyle;
import checker.Checker;
import common.Constants;

import commands.Favorite;

import fileio.ActionInputData;
import fileio.Input;
import fileio.InputLoader;
import fileio.Writer;

import commands.View;
import commands.Rating;
import org.json.JSONObject;
import org.json.simple.JSONArray;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();

        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();

        org.json.simple.JSONObject jsonObject;
        //TODO add here the entry point to your implementation

        for (ActionInputData action : input.getCommands()) {
            if (action.getActionType().equals("command")) {
                if (action.getType().equals("favorite")) {
                    Favorite favorite = new Favorite();
                    int messageCode = favorite.test(input, action);
                    if (messageCode == 0) { //exista deja in lista de favorite
                        jsonObject = fileWriter.writeFile(action.getActionId(), null,
                                "error -> " + action.getTitle()
                                        + " is already in favourite list");
                        arrayResult.add(jsonObject);
                    }
                    if (messageCode == 1) { //adaug in lista
                        jsonObject = fileWriter.writeFile(action.getActionId(), null,
                                "success -> " + action.getTitle()
                                        + " was added as favourite");
                        arrayResult.add(jsonObject);
                    }
                    if (messageCode == 2) { ////nu a fost vizualizat inca
                        jsonObject = fileWriter.writeFile(action.getActionId(), null,
                                "error -> " + action.getTitle() + " is not seen");
                        arrayResult.add(jsonObject);
                    }
                }

                if (action.getType().equals("view")) {
                    View view = new View();
                    int views = view.add(input, action);
                    jsonObject = fileWriter.writeFile(action.getActionId(), null,
                            "success -> " + action.getTitle()
                                    + " was viewed with total views of " + views);
                    arrayResult.add(jsonObject);
                }

                if (action.getType().equals("rating")) {
                    Rating rating = new Rating();
                    int messageCode = rating.test(input, action);
                    if (messageCode == 0) { //exista deja in lista de ratings
                        jsonObject = fileWriter.writeFile(action.getActionId(), null,
                                "error -> " + action.getTitle()
                                        + " is already in rating list");
                        arrayResult.add(jsonObject);
                    }
                    if (messageCode == 1) { //adaug in lista
                        jsonObject = fileWriter.writeFile(action.getActionId(), null,
                                "success -> " + action.getTitle()
                                        + " was rated with " + action.getGrade()
                                        + " by " + action.getUsername());
                        arrayResult.add(jsonObject);
                    }
                    if (messageCode == 2) {
                        jsonObject = fileWriter.writeFile(action.getActionId(), null,
                                "error -> " + action.getTitle() + " is not seen");
                        arrayResult.add(jsonObject);
                    }
                }

            }
        }


        fileWriter.closeJSON(arrayResult);
    }
}
