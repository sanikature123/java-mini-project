import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

class OnlineBookstore extends JFrame {
    private DefaultListModel<String> bookListModel;
    private JList<String> bookList;
    private JButton addToCartButton;
    private JButton checkoutButton;

    // Map to store book details (name -> [author, price])
    private Map<String, String[]> bookDetailsMap;

    public OnlineBookstore() {
        setTitle("Online Bookstore");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize book list
        bookListModel = new DefaultListModel<>();
        bookDetailsMap = new HashMap<>();

        // Add sample book details
        addBook("Harry potter and the philosopher'", new String[]{"J.K.Rowling", "$20.00"});
        addBook("The little prince", new String[]{"Antonie de saint Exupery ", "$25.00"});
        addBook("The Great Gatsby", new String[]{"F.Sctt Fitzgerald", "$18.00"});
        addBook("Politics", new String[]{"Aristotle", "$22.00"});

        bookList = new JList<>(bookListModel);
        JScrollPane scrollPane = new JScrollPane(bookList);
        add(scrollPane, BorderLayout.CENTER);

        // Initialize buttons
        addToCartButton = new JButton("Add to Cart");
        checkoutButton = new JButton("Checkout");

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addToCartButton);
        buttonPanel.add(checkoutButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners
        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToCart();
            }
        });

        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkout();
            }
        });
    }

    private void addBook(String name, String[] details) {
        bookListModel.addElement(name);
        bookDetailsMap.put(name, details);
    }

    private void addToCart() {
        int selectedIndex = bookList.getSelectedIndex();
        if (selectedIndex != -1) {
            String selectedBook = bookListModel.getElementAt(selectedIndex);
            String[] bookDetails = bookDetailsMap.get(selectedBook);
            String author = bookDetails[0];
            String price = bookDetails[1];
            JOptionPane.showMessageDialog(this, "Book: " + selectedBook + "\nAuthor: " + author + "\nPrice: " + price + "\nAdded to cart.");
        } else {
            JOptionPane.showMessageDialog(this, "Please select a book to add to cart.");
        }
    }

    private void checkout() {
        StringBuilder receipt = new StringBuilder("Receipt:\n\n");
        double total = 0;
        for (int i = 0; i < bookListModel.size(); i++) {
            String bookName = bookListModel.getElementAt(i);
            String[] details = bookDetailsMap.get(bookName);
            String priceString = details[1].replaceAll("[^\\d.]", ""); // Remove non-numeric characters
            double price = Double.parseDouble(priceString);
            total += price;
            receipt.append(bookName).append(" - ").append(details[0]).append(" - ").append(details[1]).append("\n");
        }
        receipt.append("\nTotal: $").append(total);

        JOptionPane.showMessageDialog(this, receipt.toString(), "Checkout", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                OnlineBookstore bookstore = new OnlineBookstore();
                bookstore.setVisible(true);
            }
        });
    }
}