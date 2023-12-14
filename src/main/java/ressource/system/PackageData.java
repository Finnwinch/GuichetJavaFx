package ressource.system;

import ressource.Utilisateur;

import java.io.*;
import java.util.ArrayList;

public class PackageData {
    public static void serialize(ArrayList<?> list, String fileName) {
        String desktopPath = System.getProperty("user.home") + "/Desktop/";
        File file = new File(desktopPath + fileName);
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            outputStream.writeObject(list);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static ArrayList<?> deserialize(String fileName) {
        ArrayList<?> list = null;
        try {
            String desktopPath = System.getProperty("user.home") + "/Desktop/";
            File file = new File(desktopPath + fileName);
            if (file.exists()) {
                try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))) {
                    list = (ArrayList<?>) inputStream.readObject();
                    switch (fileName) {
                        case "Transactions.ser" :
                            for (Object obj : list) {
                                Utilitaire.transactions.add((Transaction) obj) ;
                            }
                            break ;
                        case "Utilisateurs.ser" :
                            for (Object obj : list) {
                                Utilitaire.utilisateurs.add((Utilisateur) obj) ;
                            }
                            break ;
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Le fichier n'existe pas : " + file.getAbsolutePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
