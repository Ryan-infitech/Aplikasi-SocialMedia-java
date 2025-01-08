package com.rian.socialmedia;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.util.Collections;
import javax.swing.border.EmptyBorder;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;

public class Pesan extends javax.swing.JFrame {
    private final DynamoDB dynamoDB;
    private final Table messagesTable;
    private final Table usersTable;
    private final Item currentUser;
    
    private JPanel mainPanel;
    private JPanel chatPanel;
    private JTextArea messageTextArea;
    private List<Item> messages;
    
    private DefaultListModel<UserContact> contactsListModel;
    private JList<UserContact> contactsList;
    private UserContact selectedContact;

    private static class UserContact {
        String userId;
        String name;

        public UserContact(String userId, String name) {
            this.userId = userId;
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
        
    public Pesan(Item user) {
        this.currentUser = user;
        this.dynamoDB = DynamoDBConfig.getConnection();
        this.messagesTable = dynamoDB.getTable("Messages");
        this.usersTable = dynamoDB.getTable("Users");
        this.messages = new ArrayList<>();
        initializeUI();
        loadContacts();
    }

    private void initializeUI() {
        setTitle("Messages");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(960, 540);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);

        JPanel sidebar = createSidebar();
        mainPanel.add(sidebar, BorderLayout.WEST);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(230, 230, 235));

        JPanel contactsPanel = createContactsPanel();
        contentPanel.add(contactsPanel, BorderLayout.WEST);

        JPanel chatContainer = new JPanel(new BorderLayout());
        chatContainer.setBackground(new Color(230, 230, 235));

        chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
        chatPanel.setBackground(new Color(230, 230, 235));

        JScrollPane chatScrollPane = new JScrollPane(chatPanel);
        chatScrollPane.setBorder(null);
        chatContainer.add(chatScrollPane, BorderLayout.CENTER);

        JPanel messageInputPanel = createMessageInputPanel();
        chatContainer.add(messageInputPanel, BorderLayout.SOUTH);

        contentPanel.add(chatContainer, BorderLayout.CENTER);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(200, 200, 205));
        sidebar.setPreferredSize(new Dimension(200, getHeight()));
        sidebar.setBorder(new EmptyBorder(10, 10, 10, 10));

        JButton berandaBtn = createSidebarButton("Beranda");
        berandaBtn.addActionListener(e -> {
            new Beranda(currentUser).setVisible(true);
            this.dispose();
        });

        JButton pesanBtn = createSidebarButton("Pesan");
        JButton profileBtn = createSidebarButton("Profile");

        sidebar.add(berandaBtn);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(pesanBtn);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(profileBtn);
        sidebar.add(Box.createVerticalGlue());

        return sidebar;
    }

    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(180, 30));
        return button;
    }

    private JPanel createContactsPanel() {
        JPanel contactsPanel = new JPanel(new BorderLayout());
        contactsPanel.setPreferredSize(new Dimension(200, getHeight()));
        contactsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        contactsPanel.setBackground(Color.WHITE);

        contactsListModel = new DefaultListModel<>();
        contactsList = new JList<>(contactsListModel);
        contactsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        contactsList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedContact = contactsList.getSelectedValue();
                loadMessages();
            }
        });

        JScrollPane scrollPane = new JScrollPane(contactsList);
        contactsPanel.add(scrollPane, BorderLayout.CENTER);

        return contactsPanel;
    }

    private JPanel createMessageInputPanel() {
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        inputPanel.setBackground(new Color(230, 230, 235));

        messageTextArea = new JTextArea(3, 20);
        messageTextArea.setLineWrap(true);
        messageTextArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(messageTextArea);

        JButton sendButton = new JButton("Send");
        sendButton.setBackground(new Color(102, 255, 178));
        sendButton.addActionListener(e -> sendMessage());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(230, 230, 235));
        buttonPanel.add(sendButton);

        inputPanel.add(scrollPane, BorderLayout.CENTER);
        inputPanel.add(buttonPanel, BorderLayout.EAST);

        return inputPanel;
    }

    private void loadContacts() {
        try {
            ScanSpec scanSpec = new ScanSpec();
            ItemCollection<ScanOutcome> items = usersTable.scan(scanSpec);

            contactsListModel.clear();

            items.forEach(item -> {
                String userId = item.getString("userId");
                String nama = item.getString("nama");

                if (!userId.equals(currentUser.getString("userId"))) {
                    contactsListModel.addElement(new UserContact(userId, nama));
                }
            });

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading contacts: " + e.getMessage());
        }
    }

    private void sendMessage() {
        if (selectedContact == null) {
            JOptionPane.showMessageDialog(this, "Please select a contact first!");
            return;
        }

        String content = messageTextArea.getText().trim();
        if (content.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Message cannot be empty!");
            return;
        }

        try {
            String messageId = UUID.randomUUID().toString();
            String timestamp = LocalDateTime.now().toString();
            String conversationId = currentUser.getString("userId") + "-" + selectedContact.userId;

            Item newMessage = new Item()
                .withPrimaryKey("messageId", messageId)
                .withString("timestamp", timestamp)
                .withString("senderId", currentUser.getString("userId"))
                .withString("senderName", currentUser.getString("nama"))
                .withString("receiverId", selectedContact.userId)
                .withString("receiverName", selectedContact.name)
                .withString("content", content)
                .withBoolean("isRead", false)
                .withString("conversationId", conversationId);

            messagesTable.putItem(newMessage);
            messageTextArea.setText("");
            loadMessages();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error sending message: " + e.getMessage());
        }
    }

    private void loadMessages() {
        try {
            String currentUserId = currentUser.getString("userId");
            String conversationId1 = currentUserId + "-" + selectedContact.userId;
            String conversationId2 = selectedContact.userId + "-" + currentUserId;

            ScanSpec scanSpec = new ScanSpec()
                .withFilterExpression("conversationId = :id1 OR conversationId = :id2")
                .withValueMap(new ValueMap()
                    .withString(":id1", conversationId1)
                    .withString(":id2", conversationId2));

            ItemCollection<ScanOutcome> items = messagesTable.scan(scanSpec);
            messages = new ArrayList<>();
            items.forEach(messages::add);

            Collections.sort(messages, (a, b) -> 
                a.getString("timestamp").compareTo(b.getString("timestamp")));

            displayMessages();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading messages: " + e.getMessage());
        }
    }

    private void displayMessages() {
        chatPanel.removeAll();
        
        for (Item message : messages) {
            JPanel messageCard = createMessageCard(message);
            chatPanel.add(messageCard);
            chatPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        chatPanel.revalidate();
        chatPanel.repaint();
    }

    private JPanel createMessageCard(Item message) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        boolean isFromCurrentUser = message.getString("senderId")
            .equals(currentUser.getString("userId"));

        JPanel bubblePanel = new JPanel();
        bubblePanel.setLayout(new BoxLayout(bubblePanel, BoxLayout.Y_AXIS));
        bubblePanel.setBackground(isFromCurrentUser ? new Color(200, 255, 200) : Color.WHITE);
        bubblePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel userName = new JLabel(message.getString("senderName"));
        userName.setFont(new Font("Arial", Font.BOLD, 12));
        bubblePanel.add(userName);

        JTextArea content = new JTextArea(message.getString("content"));
        content.setWrapStyleWord(true);
        content.setLineWrap(true);
        content.setEditable(false);
        content.setBackground(null);
        content.setBorder(null);
        bubblePanel.add(content);

        JLabel timestamp = new JLabel(message.getString("timestamp"));
        timestamp.setFont(new Font("Arial", Font.PLAIN, 10));
        bubblePanel.add(timestamp);

        if (isFromCurrentUser) {
            card.add(Box.createHorizontalStrut(100), BorderLayout.WEST);
            card.add(bubblePanel, BorderLayout.CENTER);
        } else {
            card.add(bubblePanel, BorderLayout.CENTER);
            card.add(Box.createHorizontalStrut(100), BorderLayout.EAST);
        }

        return card;
    }

    public static void main(String args[]) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pesan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        EventQueue.invokeLater(() -> {
            new Pesan(new Item()).setVisible(true);
        });
    }
}