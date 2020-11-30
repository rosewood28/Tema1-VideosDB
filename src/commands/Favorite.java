package commands;

import fileio.ActionInputData;
import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.util.ArrayList;

public class Favorite {
    private int var;

    /**
     * @param favoriteMovies of user
     * @param title of the movie
     */
    public void add(final ArrayList<String> favoriteMovies, final String title) {
        favoriteMovies.add(title);
    }

    /**
     * @param input for testing if the user saw the video
     * @param action for testing if the user saw the video
     */
    public int test(final Input input, final ActionInputData action) {
        for (UserInputData user : input.getUsers()) { //parcurg lista de useri
            if (user.getUsername().equals(action.getUsername())) { //daca am gasit userul cautat
                for (MovieInputData movie : input.getMovies()) { //parcurg lista de filme
                    look(action, user, movie.getTitle());
                }
                for (SerialInputData serial : input.getSerials()) { //parcurg lista de seriale
                    look(action, user, serial.getTitle());
                }
            }
        }
    return var;
    }

    /**
     * @param action from command
     * @param user that has the list of favorites
     * @param title2 title to test if to add or not to the list
     */
    private void look(final ActionInputData action, final UserInputData user, final String title2) {
        if (action.getTitle().equals(title2)) { //daca am gasit filmul/serialul cautat in actiune
            for (String title : user.getHistory().keySet()) { //caut in istoricul userului videoul
                if (title.equals(title2)) { //daca am gasit videoul in istoric il pot adauga
                    int index = user.getFavoriteMovies().indexOf(title); //caut in lista de favorite
                    if (index > 0) { //filmul exista deja in lista de favorite
                        var = 0; //intorc eroare
                    } else if (index == -1) { //adaug filmul in lista de favorite
                        add(user.getFavoriteMovies(), title2); //adaug filmul in lista de favorite
                        var = 1; //am adaugat in lista
                    }
                    return; // nu stiu de ce dar aici intra si in else fara return
                } else { //filmul nu se afla in istoric
                    var = 2;
                }
            }
        }
    }

}
