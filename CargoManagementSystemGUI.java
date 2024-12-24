import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;


class CityNode {
    String cityName;
    int cityID;
    List<CityNode> subCities;

    public CityNode(String cityName, int cityID) {
        this.cityName = cityName;
        this.cityID = cityID;
        this.subCities = new ArrayList<>();
    }
}


class DeliveryTree {
    CityNode root;

    public DeliveryTree(String rootCityName, int rootCityID) {
        this.root = new CityNode(rootCityName, rootCityID);
        initializeTree();
    }

    private void initializeTree() {
        
        root = new CityNode("Central Office", 1);

        
        CityNode city1 = new CityNode("City 1", 2);
        CityNode city2 = new CityNode("City 2", 3);
        CityNode city3 = new CityNode("City 3", 4);

        root.subCities.add(city1);
        root.subCities.add(city2);
        root.subCities.add(city3);

        
        CityNode city11 = new CityNode("City 1.1", 21);
        CityNode city12 = new CityNode("City 1.2", 22);
        CityNode city13 = new CityNode("City 1.3", 23);
        city1.subCities.add(city11);
        city1.subCities.add(city12);
        city1.subCities.add(city13);

        
        CityNode city21 = new CityNode("City 2.1", 31);
        CityNode city22 = new CityNode("City 2.2", 32);
        CityNode city23 = new CityNode("City 2.3", 33);
        city2.subCities.add(city21);
        city2.subCities.add(city22);
        city2.subCities.add(city23);

        
        CityNode city31 = new CityNode("City 3.1", 41);
        CityNode city32 = new CityNode("City 3.2", 42);
        CityNode city33 = new CityNode("City 3.3", 43);
        city3.subCities.add(city31);
        city3.subCities.add(city32);
        city3.subCities.add(city33);

        
        city11.subCities.add(new CityNode("City 1.1.1", 211));
        city11.subCities.add(new CityNode("City 1.1.2", 212));
        city11.subCities.add(new CityNode("City 1.1.3", 213));

        city12.subCities.add(new CityNode("City 1.2.1", 221));
        city12.subCities.add(new CityNode("City 1.2.2", 222));
        city12.subCities.add(new CityNode("City 1.2.3", 223));

        city13.subCities.add(new CityNode("City 1.3.1", 231));
        city13.subCities.add(new CityNode("City 1.3.2", 232));
        city13.subCities.add(new CityNode("City 1.3.3", 233));

        
        city21.subCities.add(new CityNode("City 2.1.1", 311));
        city21.subCities.add(new CityNode("City 2.1.2", 312));
        city21.subCities.add(new CityNode("City 2.1.3", 313));

        city22.subCities.add(new CityNode("City 2.2.1", 321));
        city22.subCities.add(new CityNode("City 2.2.2", 322));
        city22.subCities.add(new CityNode("City 2.2.3", 323));

        city23.subCities.add(new CityNode("City 2.3.1", 331));
        city23.subCities.add(new CityNode("City 2.3.2", 332));
        city23.subCities.add(new CityNode("City 2.3.3", 333));

        
        city31.subCities.add(new CityNode("City 3.1.1", 411));
        city31.subCities.add(new CityNode("City 3.1.2", 412));
        city31.subCities.add(new CityNode("City 3.1.3", 413));

        city32.subCities.add(new CityNode("City 3.2.1", 421));
        city32.subCities.add(new CityNode("City 3.2.2", 422));
        city32.subCities.add(new CityNode("City 3.2.3", 423));

        city33.subCities.add(new CityNode("City 3.3.1", 431));
        city33.subCities.add(new CityNode("City 3.3.2", 432));
        city33.subCities.add(new CityNode("City 3.3.3", 433));
    }

    public int calculateDeliveryTime(String cityName) {
        return calculateDepth(root, cityName, 0);
    }

    private int calculateDepth(CityNode node, String cityName, int depth) {
        if (node.cityName.equals(cityName)) {
            return Math.min(4, Math.max(1, depth)); 
        }
        for (CityNode subCity : node.subCities) {
            int result = calculateDepth(subCity, cityName, depth + 1);
            if (result != -1) {
                return Math.min(4, Math.max(1, result)); 
            }
        }
        return 1; 
    }

    public List<String> getAllCities() {
        List<String> cities = new ArrayList<>();
        traverseTree(root, cities);
        return cities;
    }

    private void traverseTree(CityNode node, List<String> cities) {
        cities.add(node.cityName);
        for (CityNode subCity : node.subCities) {
            traverseTree(subCity, cities);
        }
    }
}


class Shipment {
    int shipmentID;
    String shipmentDate;
    String deliveryStatus;
    int deliveryTime;
    Shipment next;

    public Shipment(int shipmentID, String shipmentDate, String deliveryStatus, int deliveryTime) {
        this.shipmentID = shipmentID;
        this.shipmentDate = shipmentDate;
        this.deliveryStatus = deliveryStatus;
        this.deliveryTime = deliveryTime;
        this.next = null;
    }
}


class Customer {
    int customerID;
    String fullName;
    Shipment shipmentHistory; 
    Stack<Shipment> recentShipments; 

    public Customer(int customerID, String fullName) {
        this.customerID = customerID;
        this.fullName = fullName;
        this.shipmentHistory = null;
        this.recentShipments = new Stack<>();
    }

    public void addShipment(Shipment newShipment) {
        
        if (shipmentHistory == null || newShipment.shipmentDate.compareTo(shipmentHistory.shipmentDate) < 0) {
            newShipment.next = shipmentHistory;
            shipmentHistory = newShipment;
        } else {
            Shipment current = shipmentHistory;
            while (current.next != null && current.next.shipmentDate.compareTo(newShipment.shipmentDate) < 0) {
                current = current.next;
            }
            newShipment.next = current.next;
            current.next = newShipment;
        }

        
        recentShipments.push(newShipment);
        if (recentShipments.size() > 5) {
            recentShipments.remove(0); 
        }
    }

    public Stack<Shipment> getRecentShipments() {
        return recentShipments;
    }
}


public class CargoManagementSystemGUI {
    static List<Customer> customerList = new ArrayList<>();
    static DeliveryTree deliveryTree = new DeliveryTree("Central Office", 1);

    public static void main(String[] args) {
        initializeTestData();
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Kargo Yönetim Sistemi");
            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            JPanel menuPanel = new JPanel();
            menuPanel.setLayout(new GridLayout(6, 1, 10, 10));

            JButton addCustomerButton = new JButton("Yeni Müşteri Ekle");
            JButton viewHistoryButton = new JButton("Gönderim Geçmişini Görüntüle");
            JButton addShipmentButton = new JButton("Gönderim Ekle");
            JButton viewTreeButton = new JButton("Kargo Ağaç Yapısını Görüntüle");
            JButton checkStatusButton = new JButton("Kargo Durumu Sorgula");
            JButton listAllShipmentsButton = new JButton("Tüm Kargoları Listele");
            JButton viewRecentShipmentsButton = new JButton("Son 5 Gönderiyi Görüntüle");
            JButton viewDeliveredShipmentsButton = new JButton("Teslim Edilmiş Kargolar");
            JButton viewUndeliveredShipmentsButton = new JButton("Teslim Edilmemiş Kargolar");

            
            menuPanel.add(addCustomerButton);
            menuPanel.add(viewHistoryButton);
            menuPanel.add(addShipmentButton);
            menuPanel.add(viewTreeButton);
            menuPanel.add(checkStatusButton);
            menuPanel.add(listAllShipmentsButton);
            menuPanel.add(viewRecentShipmentsButton);
            menuPanel.add(viewDeliveredShipmentsButton);
            menuPanel.add(viewUndeliveredShipmentsButton);

            
            frame.add(menuPanel, BorderLayout.CENTER);

            
            addCustomerButton.addActionListener(e -> addNewCustomer(frame));
            viewHistoryButton.addActionListener(e -> viewCustomerHistory(frame));
            addShipmentButton.addActionListener(e -> addShipmentToCustomer(frame));
            viewTreeButton.addActionListener(e -> viewDeliveryTree(frame));
            checkStatusButton.addActionListener(e -> checkShipmentStatus(frame));
            listAllShipmentsButton.addActionListener(e -> listAllShipments(frame));
            viewRecentShipmentsButton.addActionListener(e -> viewRecentShipments(frame));
            viewDeliveredShipmentsButton.addActionListener(e -> viewDeliveredShipments(frame));
            viewUndeliveredShipmentsButton.addActionListener(e -> viewUndeliveredShipments(frame));

            frame.setVisible(true);
        });
    }
    private static Shipment binarySearchShipment(List<Shipment> sortedShipments, int shipmentID) {
        int low = 0, high = sortedShipments.size() - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            Shipment midShipment = sortedShipments.get(mid);

            if (midShipment.shipmentID == shipmentID) {
                return midShipment; 
            } else if (midShipment.shipmentID < shipmentID) {
                low = mid + 1; 
            } else {
                high = mid - 1; 
            }
        }

        return null; 
    }

    private static void initializeTestData() {
        List<String> allCities = deliveryTree.getAllCities(); 
        int cityCount = allCities.size();
        Random random = new Random();

        for (int i = 1; i <= 10; i++) {
            Customer customer = new Customer(i, "Customer " + i);
            customerList.add(customer);

            for (int j = 1; j <= 10; j++) {
                String shipmentDate = "2024-12-" + (j < 10 ? "0" + j : j); 
                String cityName = allCities.get(random.nextInt(cityCount)); 
                int deliveryTime = deliveryTree.calculateDeliveryTime(cityName); 
                Shipment shipment = new Shipment(j, shipmentDate, "Beklemede", deliveryTime);
                customer.addShipment(shipment); 
            }
        }
    }


    private static void addNewCustomer(JFrame frame) {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();

        Object[] message = {
                "Müşteri ID:", idField,
                "Adı ve Soyadı:", nameField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Yeni Müşteri Ekle", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int customerID = Integer.parseInt(idField.getText());
                String fullName = nameField.getText();
                customerList.add(new Customer(customerID, fullName));
                JOptionPane.showMessageDialog(frame, "Müşteri başarıyla eklendi.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Lütfen geçerli bir müşteri ID'si girin.", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private static void viewCustomerHistory(JFrame frame) {
        String customerIDStr = JOptionPane.showInputDialog(frame, "Müşteri ID'sini Girin:");
        if (customerIDStr != null) {
            try {
                int customerID = Integer.parseInt(customerIDStr);
                for (Customer customer : customerList) {
                    if (customer.customerID == customerID) {
                        StringBuilder history = new StringBuilder("Gönderim Geçmişi:\n");
                        Shipment current = customer.shipmentHistory;
                        if (current == null) {
                            history.append("Bu müşteri için gönderim geçmişi bulunmuyor.");
                        } else {
                            while (current != null) {
                                history.append("- Gönderi ID: ").append(current.shipmentID)
                                        .append(", Tarih: ").append(current.shipmentDate)
                                        .append(", Durum: ").append(current.deliveryStatus)
                                        .append(", Süre: ").append(current.deliveryTime).append(" gün\n");
                                current = current.next;
                            }
                        }
                        JOptionPane.showMessageDialog(frame, history.toString());
                        return;
                    }
                }
                JOptionPane.showMessageDialog(frame, "Müşteri bulunamadı.", "Hata", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Lütfen geçerli bir müşteri ID'si girin.", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void addShipmentToCustomer(JFrame frame) {
        JTextField customerIDField = new JTextField();
        JTextField shipmentIDField = new JTextField();
        JTextField dateField = new JTextField();
        JComboBox<String> cityComboBox = new JComboBox<>(deliveryTree.getAllCities().toArray(new String[0]));

        Object[] message = {
                "Müşteri ID:", customerIDField,
                "Gönderi ID:", shipmentIDField,
                "Gönderi Tarihi (YYYY-MM-DD):", dateField,
                "Teslimat Şehri:", cityComboBox
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Gönderim Ekle", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int customerID = Integer.parseInt(customerIDField.getText());
                int shipmentID = Integer.parseInt(shipmentIDField.getText());
                String shipmentDate = dateField.getText();
                String cityName = (String) cityComboBox.getSelectedItem();
                int deliveryTime = deliveryTree.calculateDeliveryTime(cityName);

                for (Customer customer : customerList) {
                    if (customer.customerID == customerID) {
                        Shipment newShipment = new Shipment(shipmentID, shipmentDate, "Beklemede", deliveryTime);
                        customer.addShipment(newShipment);
                        JOptionPane.showMessageDialog(frame, "Gönderim başarıyla eklendi.");
                        return;
                    }
                }
                JOptionPane.showMessageDialog(frame, "Müşteri bulunamadı.", "Hata", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Lütfen geçerli bilgiler girin.", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void viewDeliveryTree(JFrame frame) {
        DefaultMutableTreeNode rootNode = createTreeNode(deliveryTree.root);
        JTree tree = new JTree(rootNode);

        JFrame treeFrame = new JFrame("Kargo Ağaç Yapısı");
        treeFrame.setSize(400, 300);
        treeFrame.add(new JScrollPane(tree));
        treeFrame.setVisible(true);
    }

    private static DefaultMutableTreeNode createTreeNode(CityNode node) {
        DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(node.cityName + " (ID: " + node.cityID + ")");
        for (CityNode subCity : node.subCities) {
            treeNode.add(createTreeNode(subCity));
        }
        return treeNode;
    }

    private static void viewDeliveredShipments(JFrame frame) {
        List<Shipment> deliveredShipments = new ArrayList<>();
        for (Customer customer : customerList) {
            Shipment current = customer.shipmentHistory;
            while (current != null) {
                
                if (current.deliveryTime == 1) {
                    current.deliveryStatus = "Teslim Edildi";
                }
                
                if ("Teslim Edildi".equals(current.deliveryStatus)) {
                    deliveredShipments.add(current);
                }
                current = current.next;
            }
        }

        
        deliveredShipments.sort((a, b) -> Integer.compare(a.shipmentID, b.shipmentID));

        
        if (deliveredShipments.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Teslim edilmiş kargo bulunamadı.", "Bilgi", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder message = new StringBuilder("Teslim Edilmiş Kargolar:\n");
            for (Shipment shipment : deliveredShipments) {
                message.append("- Gönderi ID: ").append(shipment.shipmentID).append("\n")
                        .append("  Tarih: ").append(shipment.shipmentDate).append("\n")
                        .append("  Durum: ").append(shipment.deliveryStatus).append("\n")
                        .append("  Teslim Süresi: ").append(shipment.deliveryTime).append(" gün\n\n");
            }
            JOptionPane.showMessageDialog(frame, message.toString(), "Teslim Edilmiş Kargolar", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    
    private static void viewUndeliveredShipments(JFrame frame) {
        List<Shipment> undeliveredShipments = new ArrayList<>();
        for (Customer customer : customerList) {
            Shipment current = customer.shipmentHistory;
            while (current != null) {
                if (!"Teslim Edildi".equals(current.deliveryStatus)) {
                    undeliveredShipments.add(current);
                }
                current = current.next;
            }
        }

        
        quickSortShipments(undeliveredShipments, 0, undeliveredShipments.size() - 1);

        
        StringBuilder message = new StringBuilder("Teslim Edilmemiş Kargolar:\n");
        for (Shipment shipment : undeliveredShipments) {
            message.append("- Gönderi ID: ").append(shipment.shipmentID)
                    .append(", Tarih: ").append(shipment.shipmentDate)
                    .append(", Durum: ").append(shipment.deliveryStatus)
                    .append(", Süre: ").append(shipment.deliveryTime).append(" gün\n");
        }

        JTextArea textArea = new JTextArea(message.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JFrame listFrame = new JFrame("Teslim Edilmemiş Kargolar");
        listFrame.setSize(600, 400);
        listFrame.add(scrollPane);
        listFrame.setVisible(true);
    }

    
    private static void quickSortShipments(List<Shipment> shipments, int low, int high) {
        if (low < high) {
            int pi = partition(shipments, low, high);
            quickSortShipments(shipments, low, pi - 1);
            quickSortShipments(shipments, pi + 1, high);
        }
    }

    private static int partition(List<Shipment> shipments, int low, int high) {
        int pivot = shipments.get(high).deliveryTime;
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            if (shipments.get(j).deliveryTime <= pivot) {
                i++;
                Shipment temp = shipments.get(i);
                shipments.set(i, shipments.get(j));
                shipments.set(j, temp);
            }
        }

        Shipment temp = shipments.get(i + 1);
        shipments.set(i + 1, shipments.get(high));
        shipments.set(high, temp);

        return i + 1;
    }


    private static void listAllShipments(JFrame frame) {
        StringBuilder allShipments = new StringBuilder("Tüm Kargo Bilgileri:\n");

        for (Customer customer : customerList) {
            allShipments.append("Müşteri: ").append(customer.fullName).append(" (ID: ").append(customer.customerID).append(")\n");
            Shipment current = customer.shipmentHistory;
            if (current == null) {
                allShipments.append("  - Gönderim bulunamadı.\n");
            } else {
                while (current != null) {
                    allShipments.append("  - Gönderi ID: ").append(current.shipmentID)
                            .append(", Tarih: ").append(current.shipmentDate)
                            .append(", Durum: ").append(current.deliveryStatus)
                            .append(", Süre: ").append(current.deliveryTime).append(" gün\n");
                    current = current.next;
                }
            }
            allShipments.append("\n");
        }

        JTextArea textArea = new JTextArea(allShipments.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JFrame listFrame = new JFrame("Tüm Kargolar");
        listFrame.setSize(600, 400);
        listFrame.add(scrollPane);
        listFrame.setVisible(true);
    }
    private static void viewRecentShipments(JFrame frame) {
        String customerIDStr = JOptionPane.showInputDialog(frame, "Müşteri ID'sini Girin:");
        if (customerIDStr != null) {
            try {
                int customerID = Integer.parseInt(customerIDStr);
                for (Customer customer : customerList) {
                    if (customer.customerID == customerID) {
                        Stack<Shipment> recentShipments = customer.getRecentShipments();
                        if (recentShipments.isEmpty()) {
                            JOptionPane.showMessageDialog(frame, "Bu müşteri için gönderim geçmişi bulunmuyor.");
                            return;
                        }

                        StringBuilder recent = new StringBuilder("Son Gönderimler:\n");
                        for (Shipment shipment : recentShipments) {
                            recent.append("- Gönderi ID: ").append(shipment.shipmentID)
                                    .append(", Tarih: ").append(shipment.shipmentDate)
                                    .append(", Durum: ").append(shipment.deliveryStatus)
                                    .append(", Süre: ").append(shipment.deliveryTime).append(" gün\n");
                        }
                        JOptionPane.showMessageDialog(frame, recent.toString());
                        return;
                    }
                }
                JOptionPane.showMessageDialog(frame, "Müşteri bulunamadı.", "Hata", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Lütfen geçerli bir müşteri ID'si girin.", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private static void checkShipmentStatus(JFrame frame) {
        String shipmentIDStr = JOptionPane.showInputDialog(frame, "Gönderi ID'sini Girin:");
        if (shipmentIDStr != null) {
            try {
                int shipmentID = Integer.parseInt(shipmentIDStr);
                for (Customer customer : customerList) {
                    List<Shipment> sortedShipments = new ArrayList<>();
                    Shipment current = customer.shipmentHistory;
                    while (current != null) {
                        sortedShipments.add(current);
                        current = current.next;
                    }
                    sortedShipments.sort((a, b) -> Integer.compare(a.shipmentID, b.shipmentID));
                    Shipment result = binarySearchShipment(sortedShipments, shipmentID);
                    if (result != null) {
                        if (result.deliveryTime == 1) {
                            result.deliveryStatus = "gün içerisinde teslim ";
                        }
                        String status = "Gönderi Bilgisi:\n" +
                                "- Gönderi ID: " + result.shipmentID + "\n" +
                                "- Tarih: " + result.shipmentDate + "\n" +
                                "- Durum: " + result.deliveryStatus + "\n" +
                                "- Teslim Süresi: " + result.deliveryTime + " gün\n";
                        JOptionPane.showMessageDialog(frame, status);
                        return;
                    }
                }
                JOptionPane.showMessageDialog(frame, "Gönderi bulunamadı.", "Hata", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Lütfen geçerli bir Gönderi ID'si girin.", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


}
