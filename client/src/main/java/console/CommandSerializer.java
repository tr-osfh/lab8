package console;

import java.io.*;

public class CommandSerializer {
    public static <T extends Serializable> byte[] serialize(T object) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {

            oos.writeObject(object);
            oos.flush();
            return baos.toByteArray();

        } catch (IOException e) {
            System.err.println("Ошибка сериализации: " + e.getMessage());
            return new byte[0];
        }
    }

    public static <T> T deserialize(byte[] data) throws IOException, ClassNotFoundException {
        if (data == null || data.length == 0) {
            throw new IOException("Пустой массив данных");
        }
        try (ByteArrayInputStream bais = new ByteArrayInputStream(data);
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            return (T) ois.readObject();
        }
    }
}


