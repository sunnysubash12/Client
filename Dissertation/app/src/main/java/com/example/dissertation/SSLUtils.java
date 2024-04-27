package com.example.dissertation;
import android.content.Context;
import android.util.Log;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class SSLUtils {

    public static void initializeSSL(Context context) {

        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                // Allow all hosts
                return true;
            }
        });
        // Initialize SSL context to accept self-signed certificates
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                            // Accept all client certificates
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                            // Check server certificates
                            for (X509Certificate cert : chain) {
                                // Check if the hostname matches the expected hostname
                                String hostname = "healapp.co.uk";
                                if (!isValidHostname(cert, hostname)) {
                                    throw new CertificateException("Hostname does not match SSL certificate");
                                }
                            }
                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }

                        private boolean isValidHostname(X509Certificate cert, String hostname) throws CertificateParsingException {
                            // Extract subject alternative names (SANs) from the certificate
                            Collection<List<?>> subjectAltNames = cert.getSubjectAlternativeNames();
                            if (subjectAltNames != null) {
                                for (List<?> san : subjectAltNames) {
                                    if (san.get(1) instanceof String) {
                                        String sanHostname = (String) san.get(1);
                                        if (matchHostname(sanHostname, hostname)) {
                                            return true; // SAN matches hostname
                                        }
                                    }
                                }
                            }
                            // If SANs do not contain the hostname, check the CN (Common Name)
                            String cn = cert.getSubjectX500Principal().getName();
                            if (matchHostname(cn, hostname)) {
                                return true; // CN matches hostname
                            }
                            return false;
                        }

                        private boolean matchHostname(String pattern, String hostname) {
                            // Match the hostname against a wildcard pattern (e.g., *.example.com)
                            pattern = pattern.replace(".", "\\.");
                            pattern = pattern.replace("*", ".*");
                            return Pattern.matches(pattern, hostname);
                        }
                    }
            }, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}