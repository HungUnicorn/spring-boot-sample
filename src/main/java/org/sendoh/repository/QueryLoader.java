package org.sendoh.repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Read queries in property file
 * */
public class QueryLoader {
    private static final String propFileName = "query.properties";
    private static Properties props;

    private static Properties getQueries() throws QueryLoaderException {
        InputStream is = QueryLoader.class.getResourceAsStream("/" + propFileName);
        if (is == null) {
            throw new QueryLoaderException("Unable to load property file: " + propFileName);
        }

        if (props == null) {
            props = new Properties();
            try {
                props.load(is);
            } catch (IOException e) {
                throw new QueryLoaderException("Unable to load property file: " + propFileName + "\n" + e.getMessage());
            }
        }
        return props;
    }

    public static String getQuery(String query) throws QueryLoaderException {
        return getQueries().getProperty(query);
    }

}
