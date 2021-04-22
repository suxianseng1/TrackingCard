package com.nokida.trackingcard.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.List;

@Slf4j
public class CopyUtils {

    @SuppressWarnings("unchecked")
    public static <T> List<T> deepCopyList(List<T> src) throws Exception
    {
        List<T> dest = null;
        try
        {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(src);
            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);
            dest = (List<T>) in.readObject();
        }
        catch (IOException e)
        {
            log.error(e.getStackTrace().toString());
            throw e;
        }
        catch (ClassNotFoundException e)
        {
            log.error(e.getStackTrace().toString());
            throw e;
        }
        return dest;
    }

}
