package org.jivesoftware.smack.provider;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.PacketExtension;
import org.xmlpull.mxp1.MXParser;
import org.xmlpull.v1.XmlPullParser;

/**
 * Loads the {@link IQProvider} and {@link PacketExtensionProvider} information from a standard provider file in preparation 
 * for loading into the {@link ProviderManager}.
 * 
 * @author Robin Collier
 *
 */
public class ProviderFileLoader implements ProviderLoader {
    private final static Logger log = Logger.getLogger(ProviderFileLoader.class.getName());
    
    private Collection<IQProviderInfo> iqProviders;
    private Collection<ExtensionProviderInfo> extProviders;
    private InputStream providerStream;
    
    public ProviderFileLoader(InputStream providerFileInputStream) {
        setInputStream(providerFileInputStream);
    }
    
    public ProviderFileLoader() {
    }
    
    @Override
    public Collection<IQProviderInfo> getIQProviderInfo() {
        initialize();
        return iqProviders;
    }

    @Override
    public Collection<ExtensionProviderInfo> getExtensionProviderInfo() {
        initialize();
        return extProviders;
    }

    @SuppressWarnings("unchecked")
    private synchronized void initialize() {
        // Check to see if already initialized
        if (iqProviders != null) {
            return;
        }
        
        if (providerStream == null) {
            throw new IllegalArgumentException("No input stream set for loader");
        }
        iqProviders = new ArrayList<IQProviderInfo>();
        extProviders = new ArrayList<ExtensionProviderInfo>();
        
        // Load processing providers.
        try {
            XmlPullParser parser = new MXParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
            parser.setInput(providerStream, "UTF-8");
            int eventType = parser.getEventType();
            do {
                if (eventType == XmlPullParser.START_TAG) {
                    String typeName = parser.getName();
                    
                    try {
                        if (!"smackProviders".equals(typeName)) {
                            parser.next();
                            parser.next();
                            String elementName = parser.nextText();
                            parser.next();
                            parser.next();
                            String namespace = parser.nextText();
                            parser.next();
                            parser.next();
                            String className = parser.nextText();

                            try {
                                // Attempt to load the provider class and then create
                                // a new instance if it's an IQProvider. Otherwise, if it's
                                // an IQ class, add the class object itself, then we'll use
                                // reflection later to create instances of the class.
                                if ("iqProvider".equals(typeName)) {
                                    // Add the provider to the map.
                                    Class<?> provider = Class.forName(className);
                                    
                                    if (IQProvider.class.isAssignableFrom(provider)) {
                                        iqProviders.add(new IQProviderInfo(elementName, namespace, (IQProvider) provider.newInstance()));
                                    }
                                    else if (IQ.class.isAssignableFrom(provider)) {
                                        iqProviders.add(new IQProviderInfo(elementName, namespace, (Class<? extends IQ>)provider));
                                    }
                                }
                                else {
                                    // Attempt to load the provider class and then create
                                    // a new instance if it's an ExtensionProvider. Otherwise, if it's
                                    // a PacketExtension, add the class object itself and
                                    // then we'll use reflection later to create instances
                                    // of the class.
                                    Class<?> provider = Class.forName(className);
                                    if (PacketExtensionProvider.class.isAssignableFrom(provider)) {
                                        extProviders.add(new ExtensionProviderInfo(elementName, namespace, (PacketExtensionProvider) provider.newInstance()));
                                    }
                                    else if (PacketExtension.class.isAssignableFrom(provider)) {
                                        extProviders.add(new ExtensionProviderInfo(elementName, namespace, provider));
                                    }
                                }
                            }
                            catch (ClassNotFoundException cnfe) {
                                log.log(Level.SEVERE, "Could not find provider class", cnfe);
                            }
                        }
                    }
                    catch (IllegalArgumentException illExc) {
                        log.log(Level.SEVERE, "Invalid provider type found [" + typeName + "] when expecting iqProvider or extensionProvider", illExc);
                    }
                }
                eventType = parser.next();
            }
            while (eventType != XmlPullParser.END_DOCUMENT);
        }
        catch (Exception e){
            log.log(Level.SEVERE, "Unknown error occurred while parsing provider file", e);
        }
        finally {
            try {
                providerStream.close();
            }
            catch (Exception e) {
                // Ignore.
            }
        }
    }
    
    public void setInputStream(InputStream providerFileInput) {
        if (providerFileInput == null) {
            throw new IllegalArgumentException("InputStream cannot be null");
        }
        providerStream = providerFileInput;
        initialize();
    }
}
