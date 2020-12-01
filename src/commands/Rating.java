package commands;

import fileio.ActionInputData;
import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.util.List;

//import entertainment.Season;

public class Rating {
    private boolean bol;
    private int var = -1;
    private int ret;

    /**
     * @param input for testing if the user saw the video
     * @param action for testing if the user saw the video
     **/

    public int test(final Input input, final ActionInputData action) {
        for (UserInputData user : input.getUsers()) { //parcurg lista de useri
            if (user.getUsername().equals(action.getUsername())) { //daca am gasit userul cautat
                for (MovieInputData movie : input.getMovies()) { //parcurg lista de filme
                    var = lookMovie(action, user, movie);
                }
                for (SerialInputData serial : input.getSerials()) { //parcurg lista de seriale
                    lookSerial(action, user, serial);
                }
            }
        }
        return var;
    }

    /**
     * @param action to have access to the command
     * @param user to have the history and rating list
     * @param movie to know the movie to rate
     * @return the message code
     * 0 = movie is not in the list
     * 1 = adding in list
     * 2 = the movie is not seen
     */
    public int lookMovie(final ActionInputData action, final UserInputData user,
                                                                    final MovieInputData movie) {
        if (action.getTitle().equals(movie.getTitle())) {
            if (isInHistoryMovie(movie, user)) {
                if (!user.getRatings().isEmpty()) {
                    if (isInRatingsMovie(movie, user) == 1) {
                        var = 0;
                    } else {
                        var = 1;
                        user.getRatings().put(movie.getTitle(), action.getGrade());
                    }
                } else { //daca lista de ratings este goala, adaug
                    var = 1;
                    user.getRatings().put(movie.getTitle(), action.getGrade());
                }
            } else { //atunci filmul nu a fost visualizat inca
                var = 2;
            }
        }
        return var;
    }

    /**
     * @param action to have access to the command
     * @param user to have the history and rating list
     * @param serial to know the movie to rate
     * @return the message code
     * 0 = movie is not in the list
     * 1 = adding in list
     * 2 = the movie is not seen
     */
    public int lookSerial(final ActionInputData action, final UserInputData user,
                         final SerialInputData serial) {
        if (action.getTitle().equals(serial.getTitle())) {
            if (isInHistorySerial(serial, user)) { //daca este vizualizat ii pot da rating
                if (!user.getRatings().isEmpty()) { //daca lista de ratings nu este goala
                    if (isInRatingsSerial(serial, user) == 1) {
                        var = 0;
                    } else {
                        var = 1;
                        //adaug serialul si in lista de ratings a userului
                        user.getRatings().put(serial.getTitle(), action.getGrade());
                        int num = action.getSeasonNumber(); //nr sezon
                        //iau lista de ratings deja existenta a acelui sezon
                        List<Double> ratings = serial.getSeasons().get(num - 1).getRatings();
                        ratings.add(action.getGrade()); //adaug si acest rating
                        //actualizez lista cu noua valoare
                        serial.getSeasons().get(num - 1).setRatings(ratings);
                    }
                } else { //daca lista de ratings este goala, adaug
                    var = 1;
                    int num = action.getSeasonNumber(); //nr sezon
                    //iau lista de ratings deja existenta a acelui sezon
                    List<Double> ratings = serial.getSeasons().get(num - 1).getRatings();
                    ratings.add(action.getGrade()); //adaug si acest rating
                    //actualizez lista cu noua valoare
                    serial.getSeasons().get(num - 1).setRatings(ratings);
                    //adaug serialul si in lista de ratings a userului
                    user.getRatings().put(serial.getTitle(), action.getGrade());
                }
            } else { //atunci serialul nu a fost visualizat inca
                var = 2;
            }
        }
        return var;
    }

    /**
     * @param movie to search in history
     * @param user to take the history list
     * @return true/false
     */
    public boolean isInHistoryMovie(final MovieInputData movie, final UserInputData user) {
        for (String title : user.getHistory().keySet()) {
            bol = title.equals(movie.getTitle());
        }
        return bol;
    }


        /**
         * @param movie to search in ratings list
         * @param user to take the ratings list
         * @return true/false
         */
    public int isInRatingsMovie(final MovieInputData movie, final UserInputData user) {
        for (String ratingTitle : user.getRatings().keySet()) { //parcurg lista de ratings
            if (ratingTitle.equals(movie.getTitle())) { //daca se afla in lista de rating
                ret = 1;
            } else { //daca nu se afla in lista de rating, adaug
                ret = 0;
            }
        }
        return ret;
    }

    /**
     * @param serial to search in history
     * @param user to take the history list
     * @return true/false
     */
    public boolean isInHistorySerial(final SerialInputData serial, final UserInputData user) {
        for (String title : user.getHistory().keySet()) {
            bol = title.equals(serial.getTitle());
        }
        return bol;
    }


    /**
     * @param serial to search in ratings list
     * @param user to take the ratings list
     * @return true/false
     */
    public int isInRatingsSerial(final SerialInputData serial, final UserInputData user) {
        for (String ratingTitle : user.getRatings().keySet()) { //parcurg lista de ratings
            if (ratingTitle.equals(serial.getTitle())) { //daca se afla in lista de rating
                ret = 1;
            } else { //daca nu se afla in lista de rating, adaug
                ret = 0;
            }
        }
        return ret;
    }



















}
