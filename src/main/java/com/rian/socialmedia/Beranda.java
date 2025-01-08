package com.rian.socialmedia;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
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

public class Beranda extends javax.swing.JFrame {
    private final DynamoDB dynamoDB;
    private final Table postsTable;
    private final Item currentUser;
    private JPanel mainPanel;
    private JPanel feedPanel;
    private JTextArea postTextArea;
    private List<Item> posts;

    public Beranda(Item user) {
        this.currentUser = user;
        this.dynamoDB = DynamoDBConfig.getConnection();
        this.postsTable = dynamoDB.getTable("Posts");
        initializeUI();
        loadPosts();
    }

    private void initializeUI() {
        setTitle("Social Media");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(960, 540);
        setLocationRelativeTo(null);

        // Main container with BorderLayout
        mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);

        // Left sidebar
        JPanel sidebar = createSidebar();
        mainPanel.add(sidebar, BorderLayout.WEST);

        // Right content panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(230, 230, 235));

        // Create post section
        JPanel postPanel = createPostPanel();
        contentPanel.add(postPanel, BorderLayout.NORTH);

        // Feed panel
        feedPanel = new JPanel();
        feedPanel.setLayout(new BoxLayout(feedPanel, BoxLayout.Y_AXIS));
        feedPanel.setBackground(new Color(230, 230, 235));

        JScrollPane scrollPane = new JScrollPane(feedPanel);
        scrollPane.setBorder(null);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(200, 200, 205));
        sidebar.setPreferredSize(new Dimension(200, getHeight()));
        sidebar.setBorder(new EmptyBorder(10, 10, 10, 10));

        JButton berandaBtn = createSidebarButton("Beranda");
        JButton pesanBtn = createSidebarButton("Pesan");
        JButton profileBtn = createSidebarButton("Profile");

        // Tambahkan ActionListener untuk button Pesan
        pesanBtn.addActionListener(e -> {
            new Pesan(currentUser).setVisible(true);
            this.dispose(); // Menutup window Beranda
        });

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

    private JPanel createPostPanel() {
        JPanel postPanel = new JPanel(new BorderLayout());
        postPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        postPanel.setBackground(new Color(230, 230, 235));

        postTextArea = new JTextArea(3, 20);
        postTextArea.setLineWrap(true);
        postTextArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(postTextArea);

        JButton postButton = new JButton("Post");
        postButton.setBackground(new Color(102, 255, 178));
        postButton.addActionListener(e -> createPost());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(230, 230, 235));
        buttonPanel.add(postButton);

        postPanel.add(scrollPane, BorderLayout.CENTER);
        postPanel.add(buttonPanel, BorderLayout.SOUTH);

        return postPanel;
    }

    private void createPost() {
        String content = postTextArea.getText().trim();
        if (content.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Post cannot be empty!");
            return;
        }

        try {
            String postId = UUID.randomUUID().toString();
            Item newPost = new Item()
                .withPrimaryKey("postId", postId)
                .withString("userId", currentUser.getString("userId"))
                .withString("userName", currentUser.getString("nama"))
                .withString("content", content)
                .withNumber("likes", 0)
                .withString("createdAt", LocalDateTime.now().toString());

            postsTable.putItem(newPost);
            postTextArea.setText("");
            loadPosts();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error creating post: " + e.getMessage());
        }
    }

    private void loadPosts() {
        try {
            ScanSpec scanSpec = new ScanSpec();
            ItemCollection<ScanOutcome> items = postsTable.scan(scanSpec);
            posts = new ArrayList<>();
            items.forEach(posts::add);
            
            // Sort posts by creation date (newest first)
            Collections.sort(posts, (a, b) -> 
                b.getString("createdAt").compareTo(a.getString("createdAt")));

            displayPosts();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading posts: " + e.getMessage());
        }
    }

    private void displayPosts() {
        feedPanel.removeAll();
        
        for (Item post : posts) {
            JPanel postCard = createPostCard(post);
            feedPanel.add(postCard);
            feedPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        feedPanel.revalidate();
        feedPanel.repaint();
    }

    private JPanel createPostCard(Item post) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

        JLabel userName = new JLabel(post.getString("userName"));
        userName.setFont(new Font("Arial", Font.BOLD, 14));
        
        JTextArea content = new JTextArea(post.getString("content"));
        content.setWrapStyleWord(true);
        content.setLineWrap(true);
        content.setEditable(false);
        content.setBackground(null);
        content.setBorder(null);

        JPanel likePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        likePanel.setBackground(null);
        
        JButton likeButton = new JButton("Like");
        // Set the like button background color to red
        likeButton.setBackground(new Color(255, 0, 0));
        // Set the text color to white for better contrast
        likeButton.setForeground(Color.WHITE);
        
        JLabel likeCount = new JLabel(post.getNumber("likes") + " likes");
        
        likeButton.addActionListener(e -> handleLike(post, likeCount));
        
        likePanel.add(likeButton);
        likePanel.add(likeCount);

        card.add(userName);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(content);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(likePanel);

        return card;
    }

    private void handleLike(Item post, JLabel likeCount) {
        try {
            int currentLikes = post.getNumber("likes").intValue();
            UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey("postId", post.getString("postId"))
                .withUpdateExpression("set likes = likes + :val")
                .withValueMap(new com.amazonaws.services.dynamodbv2.document.utils.ValueMap()
                    .withNumber(":val", 1));

            postsTable.updateItem(updateItemSpec);
            likeCount.setText((currentLikes + 1) + " likes");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating likes: " + e.getMessage());
        }
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
            java.util.logging.Logger.getLogger(Beranda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        EventQueue.invokeLater(() -> {
            // Note: This is just for testing. In production, you should pass a real user Item
            new Beranda(new Item()).setVisible(true);
        });
    }
}