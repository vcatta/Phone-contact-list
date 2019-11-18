import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Loader {
    private static TreeMap<String, String> contacts = new TreeMap<>();
    private static String phoneNumber;
    private static String nameContact;
    private static final String PATTERN_PHONE_NUMBER = "(\\d{1,2})(\\d{3})(\\d{3})(\\d{2})(\\d{2})";
    private static final String REGEX_PHONE = "^(\\+?[0-9]{11,15})$";

    public static void main(String[] args) {

        for(; ;) {
            System.out.println("Введите имя или номер телефона. Просмотр списка контактов - команда LIST.");
            Scanner scanner = new Scanner(System.in);
            String request = scanner.nextLine();
            if(!validRequest(request))
            {
                continue;
            }

            if (request.toUpperCase().contains("LIST"))
            {
                if(contacts.isEmpty())
                {
                    System.out.println("В телефонной книге пока нет контактов.");
                    continue;
                }
                printContactsList(contacts);
                continue;
            }
            if(request.matches(REGEX_PHONE))
            {
               if(isListContainsPhoneNumber(request))
               {
                   printContactsByPhone();
               }
               else {
                   addNameFromUserInput();
               }
            }

            else
            {
                if(isListContainsNameContact(request))
                {
                    printContactsByName();
                }
                else {
                    addPhoneFromUserInput();
                }
            }
        }
    }

    private static boolean isListContainsPhoneNumber(String request)
    {
        phoneNumber = request.replaceAll("[^0-9]", "");
        if(contacts.containsValue(phoneNumber))
        {
            return true;
        }
        return false;
    }

    private static void printContactsByPhone()
    {
        for(Map.Entry entry: contacts.entrySet()) {
            if (entry.getValue().equals(phoneNumber))
            {
                System.out.printf("Сведения о контакте:\nТел.: %s", phoneNumber.replaceFirst
                        (PATTERN_PHONE_NUMBER, "$1 ($2) $3-$4-$5") + " , Имя: " + entry.getKey());
                System.out.println();
            }
        }
    }

    private static void addNameFromUserInput()
    {
        System.out.println("Номер не найден. Укажите имя для записи нового контакта.");
        Scanner scanner = new Scanner(System.in);
        nameContact = scanner.nextLine();
        contacts.put(nameContact.trim(), phoneNumber);
        System.out.println("Контакт успешно добавлен.");
    }

    private static boolean isListContainsNameContact(String request)
    {
        nameContact = request.trim();
        if(contacts.containsKey(nameContact))
        {
            return true;
        }
        return false;
    }

    private static void printContactsByName()
    {
        for(Map.Entry entry: contacts.entrySet()) {
            if (entry.getKey().equals(nameContact))
            {
                System.out.printf("Сведения о контакте:\nТел.: %s", contacts.get(nameContact).replaceFirst
                        (PATTERN_PHONE_NUMBER, "$1 ($2) $3-$4-$5") + " , Имя: " + nameContact);
                System.out.println();
            }
        }
    }

    private static void addPhoneFromUserInput()
    {
        System.out.println("Имя не найдено. Введите номер телефона для записи нового контакта");
        Scanner scanner = new Scanner(System.in);
        phoneNumber = scanner.nextLine();
        if(validRequest(phoneNumber))
        {
            contacts.put(nameContact, phoneNumber.replaceAll("[^0-9]", ""));
            System.out.println("Контакт успешно добавлен.");
        }
    }

    private static void printContactsList(Map<String, String> contacts)
    {
        for(String key : contacts.keySet())
        {
            System.out.printf(key + ": %s", contacts.get(key).replaceFirst(PATTERN_PHONE_NUMBER, "$1 ($2) $3-$4-$5\n"));
        }
    }

    private static boolean validRequest(String request)
    {
        if(request.matches("[^(A-Za-zА-Яа-я)]+")) {

            int countDigitsInRequest = request.replaceAll("[^0-9]", "").length();

            if (countDigitsInRequest < 11) {
                System.out.println("Номер телефона введен неполностью!");
                return false;
            }
            if(countDigitsInRequest > 15)
            {
                System.out.println("Номер телефона слишком длинный!");
                return false;
            }
        }
        return true;
    }

}
