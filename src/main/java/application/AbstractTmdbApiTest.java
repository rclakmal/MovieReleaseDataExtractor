package application;

import org.apache.commons.lang3.StringUtils;

import info.movito.themoviedbapi.TmdbApi;


public class AbstractTmdbApiTest {

    protected TmdbApi tmdb;

    // Test data
    protected static final int ID_MOVIE_BLADE_RUNNER = 78;
    protected static final int ID_MOVIE_THE_AVENGERS = 24428;
    protected static final int ID_MOVIE_DOCTOR_STRANGE = 284052;
    protected static final int ID_COLLECTION_STAR_WARS = 10;

    protected static final int ID_PERSON_BRUCE_WILLIS = 62;

    protected static final int ID_COMPANY = 2;
    protected static final String COMPANY_NAME = "Marvel Studios";

    protected static final int ID_GENRE_ACTION = 28;

    protected static final String ID_KEYWORD = "1721";

    protected static final String LANGUAGE_DEFAULT = "";
    protected static final String LANGUAGE_ENGLISH = "en";
    protected static final String LANGUAGE_RUSSIAN = "ru";
    protected static final String LANGUAGE_GERMAN = "de";


    private static String apiKey;


    public void avoidRequestCountLimit() throws InterruptedException {
        Thread.sleep(1000);
    }

    
    public AbstractTmdbApiTest() {
        apiKey = "26784eb74345d39c8e5149b1f9f985a6";

        if (StringUtils.isBlank(apiKey)) {
            String g = "Missing api key: To run test you need to provide the key as environment variable named 'apikey' " +
                    "and you have to make sure that this is key relates to a linked application";

            throw new RuntimeException(g);
        }

        tmdb = new TmdbApi(apiKey);
        
    }


    public static String getApiKey() {
        return apiKey;
    }
}
