package estansaas.fonebayad.utils;

/**
 * Created by gerald.tayag on 11/3/2015.
 */
public interface Connection {

    String STATUS_OK = "200";
    String STATUS_ACCEPTED = "202";
    String STATUS_BAD_REQUEST = "400";
    String STATUS_NOTFOUND = "404";
    String STATUS_NOTACCEPTABLE = "406";
    String STATUS_NODEVICE = "407";
    String STATUS_CONFLICT = "409";
    String STATUS_LOOP = "508";
}
