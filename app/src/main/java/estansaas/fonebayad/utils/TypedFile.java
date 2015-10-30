package estansaas.fonebayad.utils;

import com.squareup.okhttp.MediaType;

import java.io.File;

/**
 * Created by gerald.tayag on 10/23/2015.
 */
public final class TypedFile {

    private final MediaType mediaType;
    private final File file;
    private final String fileName;

    public TypedFile(MediaType mediaType, File file) {
        this(mediaType, file, file != null ? file.getName() : null);
    }

    public TypedFile(MediaType mediaType, File file, String fileName) {
        this.mediaType = mediaType;
        this.file = file;
        this.fileName = fileName;
    }

    public MediaType mediaType() {
        return mediaType;
    }

    public File file() {
        return file;
    }

    public String fileName() {
        return fileName;
    }
}
