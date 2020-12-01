package commands;

import fileio.ActionInputData;
import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.util.Map;

public class View {
    private int var = 0;

    /**
     * @param input to have access to the command
     * @param action to test if the user saw the movie for the first time or not
     * @return to give the number of the views
     */
    public int add(final Input input, final ActionInputData action) {
        for (UserInputData user : input.getUsers()) {
            if (user.getUsername().equals(action.getUsername())) { //caut userul cautat in actiune
                for (MovieInputData movie : input.getMovies()) { //caut in lista de filme
                    look(action, user, movie.getTitle());
                }
                for (SerialInputData serial : input.getSerials()) { //caut in lista de seriale
                    look(action, user, serial.getTitle());
                }
            }
        }
        return var;
    }

    /**
     * @param action to have acces to the coomand
     * @param user to verify if the movie was already seen
     * @param title to find the video
     */
    private void look(final ActionInputData action, final UserInputData user, final String title) {
        if (action.getTitle().equals(title)) {
            for (Map.Entry<String, Integer> views : user.getHistory().entrySet()) {
                if (title.equals(views.getKey())) { //filmele deja vazute
                    var = views.getValue();
                    var++; //incrementez valoarea lui var
                    views.setValue(var);
                    return;
                }
                else { //filmele care nu se afla deja in lista
                    var = 1; //var este 1
                    views.setValue(var);
                    user.getHistory().put(views.getKey(), var); //adaug video-ul in history
                }
            }
        }
    }
}
