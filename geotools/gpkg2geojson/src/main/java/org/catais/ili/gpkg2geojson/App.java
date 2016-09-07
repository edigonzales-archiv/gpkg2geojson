package org.catais.ili.gpkg2geojson;


import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.geotools.data.DataStore;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.geojson.feature.FeatureJSON;
import org.geotools.geopkg.*;
import org.opengis.feature.simple.SimpleFeature;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        File file = new File("/Users/stefan/Projekte/gpkg2geojson/data/254900-lv95.gpkg");
        
        try {
			Map params = new java.util.HashMap();
			params.put("database", file.getAbsolutePath());
			params.put("dbtype", "geopkg");
			GeoPkgDataStoreFactory factory = new GeoPkgDataStoreFactory();
			DataStore store = factory.createDataStore(params);
			
			for (String typeName : store.getTypeNames()) {
				System.out.println(typeName);
			}
			
			SimpleFeatureSource source = store.getFeatureSource("Nomenklatur_FlurnamePos");
			SimpleFeatureCollection fc = source.getFeatures();
			
			SimpleFeatureIterator iterator = fc.features();
		    try {
		        while( iterator.hasNext() ){
		            SimpleFeature feature = iterator.next();
		            System.out.println(feature.getDefaultGeometry().toString());
		            
		            FeatureJSON fjson = new FeatureJSON();
		            StringWriter writer = new StringWriter();
		            
		            fjson.writeFeature(feature, writer);
		            String json = writer.toString();   
		            System.out.println(json);
		        }
		    }
		    finally {
		        iterator.close();
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
