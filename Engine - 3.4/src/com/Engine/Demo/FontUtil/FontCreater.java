package com.Engine.Demo.FontUtil;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;

import com.Engine.Demo.FontUtil.SymbolFactory.FontMetadata;
import com.Engine.Demo.FontUtil.SymbolFactory.Symbol;
import com.Engine.Demo.FontUtil.SymbolSheetPacker.SymbolSheet;
import com.Engine.Demo.FontUtil.SymbolSheetPacker.SymbolSheetMetadata;

public class FontCreater extends JFrame implements ActionListener, ListSelectionListener, ChangeListener, WindowListener {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		FONT_CHOOSER.updateUI();
		EXPORT_CHOOSER.updateUI();
		CONFIG_CHOOSER.updateUI();
		
		new FontCreater();
	}
	
	private static final long serialVersionUID = 3621659101667585937L;
	private static final JFileChooser FONT_CHOOSER = new JFileChooser();
	private static final JFileChooser EXPORT_CHOOSER = new JFileChooser();
	private static final JFileChooser CONFIG_CHOOSER = new JFileChooser();
	
	static {
		FONT_CHOOSER.setFileSelectionMode(JFileChooser.FILES_ONLY);
		FONT_CHOOSER.setAcceptAllFileFilterUsed(false);
		FONT_CHOOSER.setFileFilter(new FileFilter() {
			public String getDescription() { return "TrueType Font (*.ttf)"; }
			public boolean accept(File f) { return f.getAbsolutePath().endsWith(".ttf") | f.isDirectory(); }
		});
		
		CONFIG_CHOOSER.setFileSelectionMode(JFileChooser.FILES_ONLY);
		CONFIG_CHOOSER.setAcceptAllFileFilterUsed(false);
		CONFIG_CHOOSER.setFileFilter(new FileFilter() {
			public String getDescription() { return "Font Config File (*.fcg)"; }
			public boolean accept(File f) { return f.getAbsolutePath().endsWith(".fcg") | f.isDirectory(); }
		});
		
		EXPORT_CHOOSER.setFileSelectionMode(JFileChooser.FILES_ONLY);
		EXPORT_CHOOSER.setAcceptAllFileFilterUsed(false);
		EXPORT_CHOOSER.setFileFilter(new FileFilter() {
			public String getDescription() { return "Qlib Font File(*.qFnt)"; }
			public boolean accept(File f) { return f.getAbsolutePath().endsWith(".qFnt") | f.isDirectory(); }
		});
	}
	
	private String prevCharSet;
	private FontMetadata prevFontMetadata;
	private ArrayList<Symbol> prevGenerated;
	
	private JPanel contentPane;
	private JTextField customFontFileTextField;
	private JButton asciiButton;
	private JButton keyboardButton;
	private JTextArea charTextArea;
	private JRadioButton systemFontButton;
	private JList<String> systemFontList;
	private JRadioButton customFontButton;
	private JButton openCustomFontButton;
	private JSpinner sizeSpinner;
	private JCheckBox boldCheckbox;
	private JCheckBox italicCheckBox;
	private JComboBox<Integer> widthComboBox;
	private JComboBox<Integer> heightComboBox;
	private JSpinner upscaleSpinner;
	private JSpinner smeerSpinner;
	private JButton previewButton;
	private JButton exportButton;
	private JButton refreshButton;
	private JTabbedPane samplePanels;
	private JPanel progressPanel;
	private JProgressBar[] progressBar;
	private JMenuItem openConfigButton;
	private JCheckBoxMenuItem useMultiChannelCheckbox;
	private JMenuItem exitButton;
	private JMenuItem saveConfigButton;
	private JMenuItem exportMenuButton;
	private JCheckBoxMenuItem multiTreadingCheckBox;
	
	private Object exportNotify;
	private SymbolSheet exportSheet;

	public FontCreater() {
		exportNotify = new Object();
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(450, 450);//525*2, 525);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		openConfigButton = new JMenuItem("Open Config...");
		fileMenu.add(openConfigButton);
		
		saveConfigButton = new JMenuItem("Save Config");
		fileMenu.add(saveConfigButton);
		
		JSeparator menuConfigSeparator = new JSeparator();
		fileMenu.add(menuConfigSeparator);
		
		exportMenuButton = new JMenuItem("Export...");
		fileMenu.add(exportMenuButton);
		
		useMultiChannelCheckbox = new JCheckBoxMenuItem("Use Multi-Channel");
		fileMenu.add(useMultiChannelCheckbox);
		
		multiTreadingCheckBox = new JCheckBoxMenuItem("Use Multi-Threading");
		multiTreadingCheckBox.setSelected(true);
		fileMenu.add(multiTreadingCheckBox);
		
		JSeparator menuExportSeporator = new JSeparator();
		fileMenu.add(menuExportSeporator);
		
		exitButton = new JMenuItem("Exit");
		fileMenu.add(exitButton);
		
		contentPane = new JPanel();/* {
			private static final long serialVersionUID = -7614772247435614333L;
			public void paintComponent(Graphics g) { draw((Graphics2D) g); }
		};*/
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(5, 0));
		setContentPane(contentPane);
		
		JPanel settingsPanel = new JPanel();
		contentPane.add(settingsPanel, BorderLayout.WEST);
		settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
		
		JPanel charPanel = new JPanel();
		settingsPanel.add(charPanel);
		charPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.RAISED, null, null), "Character Set", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		charPanel.setLayout(new BorderLayout(0, 5));
		
		JScrollPane charScrollPane = new JScrollPane();
		charPanel.add(charScrollPane);
		
		charTextArea = new JTextArea();
		charTextArea.setWrapStyleWord(true);
		charTextArea.setLineWrap(true);
		charScrollPane.setViewportView(charTextArea);
		
		Component charHeightFactor = Box.createVerticalStrut(100);
		charPanel.add(charHeightFactor, BorderLayout.WEST);
		
		JPanel charSetButtons = new JPanel();
		charPanel.add(charSetButtons, BorderLayout.SOUTH);
		charSetButtons.setLayout(new BoxLayout(charSetButtons, BoxLayout.X_AXIS));
		
		refreshButton = new JButton("Refresh");
		refreshButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		refreshButton.setBorder(new EmptyBorder(3, 3, 3, 3));
		charSetButtons.add(refreshButton);
		
		Component charButtonShift = Box.createHorizontalGlue();
		charSetButtons.add(charButtonShift);
		
		asciiButton = new JButton("ASCII");
		asciiButton.setToolTipText("");
		charSetButtons.add(asciiButton);
		
		keyboardButton = new JButton("Keyboard");
		keyboardButton.setToolTipText("All Characters found on a Standard US Keyboard");
		charSetButtons.add(keyboardButton);
		
		JPanel fontPanel = new JPanel();
		fontPanel.setMaximumSize(new Dimension(32766, 32766));
		settingsPanel.add(fontPanel);
		fontPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.RAISED, null, null), "Font Setting", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagLayout gbl_fontPanel = new GridBagLayout();
		gbl_fontPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_fontPanel.rowHeights = new int[]{130, 33, 0, 0, 0};
		gbl_fontPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_fontPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		fontPanel.setLayout(gbl_fontPanel);
		
		systemFontButton = new JRadioButton("System:");
		GridBagConstraints gbc_systemFontButton = new GridBagConstraints();
		gbc_systemFontButton.anchor = GridBagConstraints.NORTHEAST;
		gbc_systemFontButton.insets = new Insets(0, 0, 5, 5);
		gbc_systemFontButton.gridx = 0;
		gbc_systemFontButton.gridy = 0;
		fontPanel.add(systemFontButton, gbc_systemFontButton);
		
		JSeparator fontVerticalSerperator = new JSeparator();
		fontVerticalSerperator.setOrientation(SwingConstants.VERTICAL);
		GridBagConstraints gbc_fontVerticalSerperator = new GridBagConstraints();
		gbc_fontVerticalSerperator.fill = GridBagConstraints.VERTICAL;
		gbc_fontVerticalSerperator.gridheight = 4;
		gbc_fontVerticalSerperator.insets = new Insets(0, 0, 0, 5);
		gbc_fontVerticalSerperator.gridx = 1;
		gbc_fontVerticalSerperator.gridy = 0;
		fontPanel.add(fontVerticalSerperator, gbc_fontVerticalSerperator);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 5;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 0;
		fontPanel.add(scrollPane, gbc_scrollPane);
		
		systemFontList = new JList<>(new DefaultListModel<>());
		scrollPane.setViewportView(systemFontList);
		
		customFontButton = new JRadioButton("Custom:");
		GridBagConstraints gbc_customFontButton = new GridBagConstraints();
		gbc_customFontButton.anchor = GridBagConstraints.EAST;
		gbc_customFontButton.insets = new Insets(0, 0, 5, 5);
		gbc_customFontButton.gridx = 0;
		gbc_customFontButton.gridy = 1;
		fontPanel.add(customFontButton, gbc_customFontButton);
		
		customFontFileTextField = new JTextField();
		GridBagConstraints gbc_customFontFileTextField = new GridBagConstraints();
		gbc_customFontFileTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_customFontFileTextField.gridwidth = 4;
		gbc_customFontFileTextField.insets = new Insets(0, 0, 5, 5);
		gbc_customFontFileTextField.gridx = 2;
		gbc_customFontFileTextField.gridy = 1;
		fontPanel.add(customFontFileTextField, gbc_customFontFileTextField);
		customFontFileTextField.setColumns(10);
		
		openCustomFontButton = new JButton("Open");
		GridBagConstraints gbc_openCustomFontButton = new GridBagConstraints();
		gbc_openCustomFontButton.anchor = GridBagConstraints.EAST;
		gbc_openCustomFontButton.insets = new Insets(0, 0, 5, 0);
		gbc_openCustomFontButton.gridx = 6;
		gbc_openCustomFontButton.gridy = 1;
		fontPanel.add(openCustomFontButton, gbc_openCustomFontButton);
		
		JSeparator fontSettingsSeperator = new JSeparator();
		GridBagConstraints gbc_fontSettingsSeperator = new GridBagConstraints();
		gbc_fontSettingsSeperator.fill = GridBagConstraints.HORIZONTAL;
		gbc_fontSettingsSeperator.gridwidth = 5;
		gbc_fontSettingsSeperator.insets = new Insets(0, 0, 5, 0);
		gbc_fontSettingsSeperator.gridx = 2;
		gbc_fontSettingsSeperator.gridy = 2;
		fontPanel.add(fontSettingsSeperator, gbc_fontSettingsSeperator);
		
		JLabel siazeLabel = new JLabel("Size: ");
		GridBagConstraints gbc_siazeLabel = new GridBagConstraints();
		gbc_siazeLabel.anchor = GridBagConstraints.EAST;
		gbc_siazeLabel.insets = new Insets(0, 0, 0, 5);
		gbc_siazeLabel.gridx = 0;
		gbc_siazeLabel.gridy = 3;
		fontPanel.add(siazeLabel, gbc_siazeLabel);
		
		sizeSpinner = new JSpinner();
		GridBagConstraints gbc_sizeSpinner = new GridBagConstraints();
		gbc_sizeSpinner.anchor = GridBagConstraints.NORTH;
		gbc_sizeSpinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_sizeSpinner.insets = new Insets(0, 0, 0, 5);
		gbc_sizeSpinner.gridx = 2;
		gbc_sizeSpinner.gridy = 3;
		fontPanel.add(sizeSpinner, gbc_sizeSpinner);
		sizeSpinner.setModel(new SpinnerNumberModel(32, 10, 65536, 1));
		
		boldCheckbox = new JCheckBox("Bold");
		GridBagConstraints gbc_boldChackbox = new GridBagConstraints();
		gbc_boldChackbox.anchor = GridBagConstraints.NORTH;
		gbc_boldChackbox.insets = new Insets(0, 0, 0, 5);
		gbc_boldChackbox.gridx = 3;
		gbc_boldChackbox.gridy = 3;
		fontPanel.add(boldCheckbox, gbc_boldChackbox);
		
		italicCheckBox = new JCheckBox("Italic");
		GridBagConstraints gbc_italicCheckBox = new GridBagConstraints();
		gbc_italicCheckBox.anchor = GridBagConstraints.NORTH;
		gbc_italicCheckBox.insets = new Insets(0, 0, 0, 5);
		gbc_italicCheckBox.gridx = 4;
		gbc_italicCheckBox.gridy = 3;
		fontPanel.add(italicCheckBox, gbc_italicCheckBox);
		
		JPanel texturePanel = new JPanel();
		settingsPanel.add(texturePanel);
		texturePanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.RAISED, null, null), "Texture Settings", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		texturePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
		JPanel textureLockPanel = new JPanel();
		texturePanel.add(textureLockPanel);
		textureLockPanel.setLayout(new GridLayout(0, 3, 5, 0));
		
		JPanel textureSizePanel = new JPanel();
		textureLockPanel.add(textureSizePanel);
		textureSizePanel.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new EmptyBorder(3, 3, 3, 3)));
		textureSizePanel.setLayout(new GridLayout(2, 2, 3, 0));
		
		JLabel textureWidthLabel = new JLabel("Width");
		textureWidthLabel.setHorizontalAlignment(SwingConstants.CENTER);
		textureSizePanel.add(textureWidthLabel);
		
		JLabel textureHeightLabel = new JLabel("Height");
		textureHeightLabel.setHorizontalAlignment(SwingConstants.CENTER);
		textureSizePanel.add(textureHeightLabel);
		
		widthComboBox = new JComboBox<>();
		textureSizePanel.add(widthComboBox);
		widthComboBox.setModel(new DefaultComboBoxModel<>(new Integer[] {32, 64, 128, 256, 512, 1024, 2048}));
		widthComboBox.setSelectedIndex(4);
		
		heightComboBox = new JComboBox<>();
		heightComboBox.setModel(new DefaultComboBoxModel<>(new Integer[] {32, 64, 128, 256, 512, 1024, 2048}));
		heightComboBox.setSelectedIndex(4);
		textureSizePanel.add(heightComboBox);
		
		JPanel textureEffectPanel = new JPanel();
		textureLockPanel.add(textureEffectPanel);
		textureEffectPanel.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new EmptyBorder(3, 3, 3, 3)));
		textureEffectPanel.setLayout(new GridLayout(2, 2, 0, 3));
		
		JLabel upscaleLabel = new JLabel("Upscale: ");
		upscaleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		textureEffectPanel.add(upscaleLabel);
		
		upscaleSpinner = new JSpinner();
		textureEffectPanel.add(upscaleSpinner);
		upscaleSpinner.setModel(new SpinnerNumberModel(1, 1, 64, 1));
		
		JLabel smeerLabel = new JLabel("Smear: ");
		smeerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		textureEffectPanel.add(smeerLabel);
		
		smeerSpinner = new JSpinner();
		textureEffectPanel.add(smeerSpinner);
		smeerSpinner.setModel(new SpinnerNumberModel(10.0, 1.0, 64.0, 1.0));
		
		JPanel textureOutputPanel = new JPanel();
		textureLockPanel.add(textureOutputPanel);
		textureOutputPanel.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new EmptyBorder(3, 3, 3, 3)));
		textureOutputPanel.setLayout(new GridLayout(2, 1, 0, 1));
		
		previewButton = new JButton("Calculate Preview");
		textureOutputPanel.add(previewButton);
		
		exportButton = new JButton("Calculate Export");
		textureOutputPanel.add(exportButton);

		String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		for(String font : fonts) ((DefaultListModel<String>) systemFontList.getModel()).addElement(font);
		systemFontList.setSelectedIndex(0);
		
		systemFontButton.addActionListener(this);
		customFontButton.addActionListener(this);
		
		openCustomFontButton.addActionListener(this);
		
		refreshButton.addActionListener(this);
		boldCheckbox.addActionListener(this);
		italicCheckBox.addActionListener(this);
		sizeSpinner.addChangeListener(this);
		systemFontList.addListSelectionListener(this);
		
		keyboardButton.addActionListener(this);
		asciiButton.addActionListener(this);
		
		previewButton.addActionListener(this);
		exportButton.addActionListener(this);
		
		exitButton.addActionListener(this);
		saveConfigButton.addActionListener(this);
		openConfigButton.addActionListener(this);
		exportMenuButton.addActionListener(this);
		
		this.addWindowListener(this);
		
		ButtonGroup fontSourceGroup = new ButtonGroup();
		fontSourceGroup.add(systemFontButton);
		fontSourceGroup.add(customFontButton);
		systemFontButton.setSelected(true);
		
		actionPerformed(new ActionEvent(keyboardButton, 0, ""));
		actionPerformed(new ActionEvent(systemFontButton, 0, ""));
		
		JPanel viewPanel = new JPanel();
		contentPane.add(viewPanel, BorderLayout.CENTER);
		viewPanel.setLayout(new BorderLayout(0, 5));
		
		samplePanels = new JTabbedPane(JTabbedPane.TOP);
		viewPanel.add(samplePanels);
		samplePanels.addTab("Page 1", new SamplePanel(new BufferedImage(512, 512, BufferedImage.TYPE_BYTE_GRAY)));
		
		progressPanel = new JPanel();
		viewPanel.add(progressPanel, BorderLayout.SOUTH);
		progressPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public static interface ProgressNotify { public void updateProgressBar(String message, int value, int max, int index, int count); }
	public void updateProgressBar(String message, int value, int max, int index, int count) {
		if(count > 0 && (progressBar == null || count != progressBar.length)) {
			progressPanel.removeAll();
			progressPanel.setLayout(new GridLayout(0, count, 0, 0));
			progressBar = new JProgressBar[count];
			
			for(int i = 0; i < count; i ++) {
				progressBar[i] = new JProgressBar();
				progressPanel.add(progressBar[i]);
				progressBar[i].setForeground(new Color(50, 205, 50));
				progressBar[i].setStringPainted(true);
			}
		}
		
		progressBar[index].setString(message);
		progressBar[index].setMaximum(max);
		progressBar[index].setValue(value);
	}
	
	private Font createFont() {
		if(systemFontButton.isSelected()) {
			int style = (boldCheckbox.isSelected() ? Font.BOLD : 0) | (italicCheckBox.isSelected() ? Font.ITALIC : 0);
			return new Font(systemFontList.getSelectedValue(), style, (Integer) sizeSpinner.getValue());
			
		} else {
			try {
				if(customFontFileTextField.getText().isEmpty()) return null;
				
				Font font = Font.createFont(Font.TRUETYPE_FONT, new File(customFontFileTextField.getText()));
				return font.deriveFont((Integer) sizeSpinner.getValue() * 1f);
				
			} catch(FontFormatException | IOException e) {
				JOptionPane.showMessageDialog(this, e, "Failed to load Font", JOptionPane.ERROR_MESSAGE);
				return null;
			}
		}
	}
	
	private FontMetadata createFontMetadata() {
		FontMetadata fontMeta = new FontMetadata();
		fontMeta.font = createFont();
		if(fontMeta.font == null) {
			JOptionPane.showMessageDialog(this, "Invalid Font!", "Font Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		
		fontMeta.context = new FontRenderContext(null, false, false);
		fontMeta.metrics = getFontMetrics(fontMeta.font);
		fontMeta.upscale = (Integer) upscaleSpinner.getValue();
		fontMeta.smeer = ((Double) smeerSpinner.getValue()).floatValue();
		
		return fontMeta;
	}
	
	private SymbolSheetMetadata createSheetMetadata(FontMetadata fontMeta) {
		SymbolSheetMetadata sheetMeta = new SymbolSheetMetadata();
		sheetMeta.font = fontMeta;
		sheetMeta.width = (Integer) widthComboBox.getSelectedItem();
		sheetMeta.height = (Integer) heightComboBox.getSelectedItem();
		sheetMeta.useMultiChannel = true;
		
		return sheetMeta;
	}
	
	private int threadsDone;
	private static final int THREAD_COUNT = 4;
	
	private class SymbolThread extends Thread {
		private char[] chars; 
		private int startIndex, stride;
		private List<Symbol> symbols;
		private FontMetadata meta;
		private ProgressNotify p;
		private int index;

		public SymbolThread(FontMetadata meta, char[] chars, int startIndex, int stride, int index, List<Symbol> symbols, ProgressNotify p) {
			this.chars = chars;
			this.startIndex = startIndex;
			this.stride = stride;
			this.symbols = symbols;
			this.index = index;
			this.meta = meta;
			this.p = p;
		}

		public void run() {
			for(int i = startIndex; i < startIndex + stride; i ++) {
				char symbol = chars[i];
				if(symbol != ' ' && Character.isWhitespace(symbol)) continue;
				p.updateProgressBar("Smeering '" + symbol + "'...", i - startIndex, stride, index, -1);
				
				symbols.add(SymbolFactory.createSymbol(symbol, meta));
			}
			
			threadsDone ++;
			p.updateProgressBar("Done", stride, stride, index, -1);
			synchronized(symbols) {
				symbols.notify();
			}
		}
	}
	
	private ArrayList<Symbol> createSymbols(FontMetadata fontMeta) {
		if(charTextArea.getText().equals(prevCharSet))
		if(fontMeta.equals(prevFontMetadata)) return prevGenerated;
		else if(prevFontMetadata != null && fontMeta.upscale < prevFontMetadata.upscale) {
			int tempPrevScale = fontMeta.upscale;
			fontMeta.upscale = prevFontMetadata.upscale;
			if(fontMeta.equals(prevFontMetadata)) return prevGenerated;
			fontMeta.upscale = tempPrevScale;
		}
		
		char[] charsArrays = charTextArea.getText().toCharArray();
		List<Symbol> symbols = Collections.synchronizedList(new ArrayList<>());

		int iterCount = Math.min(charsArrays.length, multiTreadingCheckBox.isSelected() ? THREAD_COUNT : 1);
		int stride = charsArrays.length / iterCount;
		updateProgressBar("", 0, 1, 0, iterCount);
		for(int i = 0; i < iterCount; i ++) {
			SymbolThread thread = new SymbolThread(fontMeta, charsArrays, i * stride, stride + 
					(i + 1 == iterCount ? charsArrays.length % iterCount : 0), i, symbols, this::updateProgressBar);
			thread.start();
		}

		threadsDone = 0;
		while(threadsDone < iterCount) {
			synchronized(symbols) { try { symbols.wait(); } 
			catch(InterruptedException e) { continue; } }
		}
		
		symbols.add(SymbolFactory.createSymbol(0xFFFD, fontMeta));
		
		prevCharSet = charTextArea.getText();
		prevFontMetadata = fontMeta;
		prevGenerated = new ArrayList<>(symbols);
		
		return prevGenerated;
	}
	
	private void displaySheets(SymbolSheet sheet) {
		samplePanels.removeAll(); int index = 0;
		for(int i = 0; i < sheet.pages.length; i ++) {
			updateProgressBar("Displaying Page " + (i + 1) + " of " + (sheet.pages.length), ++ index, sheet.pages.length, 0, 1);
			samplePanels.addTab("Page " + (i + 1), new SamplePanel(sheet.pages[i]));
		}
	}
	
	private void updateFontSample() {
		Font font = createFont();
		if(font == null) return;
		
		charTextArea.setFont(font.deriveFont(18f));
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == openCustomFontButton) {
			if(FONT_CHOOSER.showOpenDialog(this) == JFileChooser.CANCEL_OPTION) return;
			
			File file = FONT_CHOOSER.getSelectedFile();
			String oldPath = customFontFileTextField.getText();
			customFontFileTextField.setText(file.getAbsolutePath());
			
			if(createFont() == null)
				customFontFileTextField.setText(oldPath);
			
			updateFontSample();
			return;
		}
		
		if(e.getSource() == systemFontButton || e.getSource() == customFontButton) {
			systemFontList.setEnabled(systemFontButton.isSelected());
			boldCheckbox.setEnabled(systemFontButton.isSelected());
			italicCheckBox.setEnabled(systemFontButton.isSelected());
			
			openCustomFontButton.setEnabled(!systemFontButton.isSelected());
			customFontFileTextField.setEnabled(!systemFontButton.isSelected());
			
			updateFontSample();
			return;
		}
		
		if(e.getSource() == boldCheckbox || e.getSource() == italicCheckBox | e.getSource() == refreshButton) {
			updateFontSample();
			return;
		}
		
		if(e.getSource() == keyboardButton) {
			charTextArea.setText("ABCDEFGHIJKLMNOPQRSTUVWXYZ\n"
					+ "abcdefghijklmnopqrstuvwxyz\n"
					+ "0123456789\n"
					+ "?!@#$%^&*()[]{}<>/\\`~';:\",._-+=");
			return;
		}
		
		if(e.getSource() == asciiButton) {
			StringBuilder chars = new StringBuilder(127 - ' ');
			for(int i = ' '; i < 127; i ++) if(!Character.isWhitespace(i)) chars.append((char) i);
			charTextArea.setText(chars.toString());
			
			return;
		}
		
		if(e.getSource() == previewButton) {
			samplePanels.removeAll();
			samplePanels.addTab("Page 1", new SamplePanel(new BufferedImage(512, 512, BufferedImage.TYPE_BYTE_GRAY)));
			
			previewButton.setEnabled(false);
			exportButton.setEnabled(false);
			exportMenuButton.setEnabled(false);
			
			new Thread(() -> {
				FontMetadata fontMeta = createFontMetadata();
				if(fontMeta == null) {
					previewButton.setEnabled(true);
					exportButton.setEnabled(true);
					return;
				}
				
				fontMeta.upscale = 1;
				ArrayList<Symbol> symbols = createSymbols(fontMeta);
				
				SymbolSheetMetadata sheetMeta = createSheetMetadata(fontMeta);
				sheetMeta.useMultiChannel = false;
				SymbolSheet sheet = SymbolSheetPacker.pack(sheetMeta, this::updateProgressBar, symbols);
				if(sheet == null) return;
				displaySheets(sheet);

				previewButton.setEnabled(true);
				exportButton.setEnabled(true);
				exportMenuButton.setEnabled(true);
			}, "Preview Processing Thread").start();
			
			return;
		}
		
		if(e.getSource() == exportButton) {
			previewButton.setEnabled(false);
			exportButton.setEnabled(false);
			exportMenuButton.setEnabled(false);
			
			new Thread(() -> {
				FontMetadata fontMeta = createFontMetadata();
				if(fontMeta == null) {
					previewButton.setEnabled(true);
					exportButton.setEnabled(true);
					return;
				}
				
				ArrayList<Symbol> symbols = createSymbols(fontMeta);
				
				SymbolSheetMetadata sheetMeta = createSheetMetadata(fontMeta); 
				sheetMeta.useMultiChannel = useMultiChannelCheckbox.isSelected();
				SymbolSheet sheet = exportSheet = SymbolSheetPacker.pack(sheetMeta, this::updateProgressBar, symbols);
				if(sheet == null) return;
				displaySheets(sheet);

				previewButton.setEnabled(true);
				exportButton.setEnabled(true);
				exportMenuButton.setEnabled(true);
				
				synchronized(exportNotify) { exportNotify.notify(); }
			}, "Export Processing Thread").start();
			
			return;
		}
		
		if(e.getSource() == exitButton) {
			windowClosed(null);
			return;
		}
		
		if(e.getSource() == saveConfigButton) {
			int result = CONFIG_CHOOSER.showSaveDialog(this);
			if(result == JFileChooser.CANCEL_OPTION) return;
			
			File file = CONFIG_CHOOSER.getSelectedFile();
			if(!file.getAbsolutePath().endsWith(".fcg"))
				file = new File(file.getAbsoluteFile() + ".fcg");
			
			if(file.exists()) {
				int overrideResult = JOptionPane.showConfirmDialog(this, "Would you like to override the selected File?", 
						file.getName() + " already Exists!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if(overrideResult == JOptionPane.NO_OPTION) {
					actionPerformed(e); return;
				}
			}
			
			try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
				out.writeUTF(charTextArea.getText());
				out.writeBoolean(customFontButton.isSelected());
				
				FontMetadata fontMeta = createFontMetadata();
				if(customFontButton.isSelected()) {
					fontMeta.font = null;
					out.writeUTF(customFontFileTextField.getText());
					out.writeInt((Integer) sizeSpinner.getValue());
				}
				
				out.writeObject(createSheetMetadata(fontMeta));
			} catch(IOException er) {
				JOptionPane.showMessageDialog(this, er, "An error Occured while Saving", JOptionPane.ERROR_MESSAGE);
			}
			
			return;
		}
		
		if(e.getSource() == openConfigButton) {
			int result = CONFIG_CHOOSER.showOpenDialog(this);
			if(result == JFileChooser.CANCEL_OPTION) return;
			
			try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(CONFIG_CHOOSER.getSelectedFile()))) {
				charTextArea.setText(in.readUTF());
				customFontButton.setSelected(in.readBoolean());
				
				if(customFontButton.isSelected()) {
					customFontFileTextField.setText(in.readUTF());
					sizeSpinner.setValue(in.readInt());
				}
				
				SymbolSheetMetadata sheetMetadata = (SymbolSheetMetadata) in.readObject();
				FontMetadata fontMeta = sheetMetadata.font;
				
				upscaleSpinner.setValue(fontMeta.upscale);
				smeerSpinner.setValue((double) fontMeta.smeer);
				
				widthComboBox.setSelectedItem(sheetMetadata.width);
				heightComboBox.setSelectedItem(sheetMetadata.height);
				useMultiChannelCheckbox.setSelected(sheetMetadata.useMultiChannel);
				
				if(!customFontButton.isSelected()) {
					Font font = fontMeta.font;
					systemFontList.setSelectedValue(font.getName(), true);
					boldCheckbox.setSelected((font.getStyle() & Font.BOLD) != 0);
					italicCheckBox.setSelected((font.getStyle() & Font.ITALIC) != 0);
				}
				
				updateFontSample();
				actionPerformed(new ActionEvent(systemFontButton, 0, ""));
			} catch(IOException | ClassNotFoundException er) {
				JOptionPane.showMessageDialog(this, er, "An error Occured while Saving", JOptionPane.ERROR_MESSAGE);
			}
			
			return;
		}
		
		if(e.getSource() == exportMenuButton) {
			exportMenuButton.setEnabled(false);
			
			while(true) {
				EXPORT_CHOOSER.setSelectedFile(new File(createFont().getName() + ".qFnt"));
				int result = EXPORT_CHOOSER.showSaveDialog(this);
				if(result == JFileChooser.CANCEL_OPTION) return;
			
				File file = EXPORT_CHOOSER.getSelectedFile();
				if(!file.getAbsolutePath().toLowerCase().endsWith(".qfnt"))
					file = new File(file.getAbsolutePath() + ".qFnt");
				
				if(file.exists()) {
					int conResult = JOptionPane.showConfirmDialog(this, "Do you want to Overwrite the selected file?", 
							"File already Exists", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if(conResult != JOptionPane.YES_OPTION) continue;
				}
				
				EXPORT_CHOOSER.setSelectedFile(file);
				break;
			}
			
			new Thread(() -> {
				actionPerformed(new ActionEvent(exportButton, 0, ""));
				synchronized(exportNotify) { try { exportNotify.wait(); } catch(InterruptedException ex) {} }
				
				if(exportSheet == null) {
					JOptionPane.showMessageDialog(this, "An error has occured while creating the Font!",
							"Failed to create Font", JOptionPane.ERROR_MESSAGE);
					
					exportMenuButton.setEnabled(true);
					return;
				}
				
				try(ZipOutputStream out = new ZipOutputStream(new FileOutputStream(EXPORT_CHOOSER.getSelectedFile()))) {
					out.putNextEntry(new ZipEntry("data.xml"));
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
					writer.write(exportSheet.symbolData.createTag("")); writer.flush();
					
					for(int i = 0; i < exportSheet.pages.length; i ++) {
						out.putNextEntry(new ZipEntry(i + ".png"));
						ImageIO.write(exportSheet.pages[i], "png", out);
					}
				} catch(IOException er) {
					JOptionPane.showMessageDialog(this, er, "An error Occured while Saving", JOptionPane.ERROR_MESSAGE);
					
					exportMenuButton.setEnabled(true);
					return;
				}

				exportMenuButton.setEnabled(true);
			}, "Export Thread").start();
			
			return;
		}
	}
	
	static class SamplePanel extends JPanel {
		private static final long serialVersionUID = 1L;
		
		private BufferedImage img;
		public SamplePanel(BufferedImage img) { 
			this.img = img; 
			
			setPreferredSize(new Dimension(522, 522));
			setMaximumSize(getPreferredSize());
			setMinimumSize(getPreferredSize());
		} 
		
		public void paintComponent(Graphics g) {
			g.setColor(Color.BLACK);
			g.fillRect(5, 5, 512, 512);
			g.drawImage(img, 5, 5, 512, 512, null);
		}
	}
	
	public void valueChanged(ListSelectionEvent e) {
		if(e.getSource() == systemFontList) {
			updateFontSample();
			return;
		}
	}

	public void stateChanged(ChangeEvent e) {
		if(e.getSource() == sizeSpinner) {
			updateFontSample();
			return;
		}
	}
	
	public void windowClosed(WindowEvent e) {
		int result = JOptionPane.showConfirmDialog(this, "Would you like to save your configuration befor Exiting?", 
				"Save Config", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
		if(result == JOptionPane.CANCEL_OPTION) return;
		if(result == JOptionPane.YES_OPTION) actionPerformed(new ActionEvent(saveConfigButton, 0, ""));
		
		setVisible(false);
		dispose();
		System.exit(0);
	}
	
	{
		/*
		*	private void filter(Graphics2D og) {
				String str = "A";
				BufferedImage raw = new BufferedImage(contentPane.getWidth(), contentPane.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
				Graphics2D g = raw.createGraphics(); {
					g.setFont(new Font("Times New Roman", Font.PLAIN, 512));
					FontMetrics metrics = g.getFontMetrics();
					Rectangle2D bounds = metrics.getStringBounds(str, g);
					
					double x = (raw.getWidth() - bounds.getWidth()) / 2;
					double y = (raw.getHeight() - bounds.getHeight()) / 2;
					g.translate(x, y - bounds.getY());

					g.setColor(new Color(1, 1, 1, 1f));
					g.drawString(str, 0, 0);
				} g.dispose();
				
//				try {
//					raw = ImageIO.read(RenderTesting.class.getResource("/textures/Base.png"));
//				} catch(IOException e) {}
				
				float[] gaussian = new float[] {
//					0.00598f, 0.060626f, 0.241843f, 0.383103f, 0.241843f, 0.060626f, 0.00598f
						
//					0.0093f, 0.028002f, 0.065984f, 0.121703f, 0.175713f, 0.198596f, 0.175713f, 
//					0.121703f, 0.065984f, 0.028002f, 0.0093f
						
//					0.011254f, 0.016436f, 0.023066f, 0.031105f, 0.040306f, 0.050187f, 0.060049f,
//					0.069041f, 0.076276f, 0.080977f, 0.082607f, 0.080977f, 0.076276f, 0.069041f,
//					0.060049f, 0.050187f, 0.040306f, 0.031105f, 0.023066f, 0.016436f, 0.011254f
						
					0.000001f, 0.00001f, 0.000078f, 0.000489f, 0.002403f, 0.009245f, 0.027835f, 
					0.065591f, 0.120978f, 0.174666f, 0.197413f, 0.174666f, 0.120978f, 0.065591f, 
					0.027835f, 0.009245f, 0.002403f, 0.000489f, 0.000078f, 0.00001f, 0.000001f


				};
				
				BufferedImage blurH = new BufferedImage(raw.getWidth(), raw.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
				g = blurH.createGraphics(); {
					Kernel kernel = new Kernel(7, 1, gaussian);
					BufferedImageOp imageOp = new ConvolveOp(kernel);
					g.drawImage(raw, imageOp, 0, 0);
				} g.dispose();
				
				BufferedImage blurV = new BufferedImage(raw.getWidth(), raw.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
				g = blurV.createGraphics(); {
					Kernel kernel = new Kernel(1, 7, gaussian);
					BufferedImageOp imageOp = new ConvolveOp(kernel);
					g.drawImage(blurH, imageOp, 0, 0);
				} g.dispose();
				
				BufferedImage sobelX = new BufferedImage(raw.getWidth(), raw.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
				g = sobelX.createGraphics(); {
					Kernel kernel = new Kernel(3, 3, new float[] {
							-1, 0, 1,
							-2, 0, 2,
							-1, 0, 1
					});
					
//					BufferedImageOp imageOp = new ConvolveOp(kernel);
//					g.drawImage(blurV, imageOp, 0, 0);
					
					g.drawImage(apply(raw, kernel), 0, 0, null);
				} g.dispose();
				
				BufferedImage sobelY = new BufferedImage(raw.getWidth(), raw.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
				g = sobelY.createGraphics(); {
					Kernel kernel = new Kernel(3, 3, new float[] {
							-1, -2, -1,
							 0,  0,  0,
							 1,  2,  1
					});
					
//					BufferedImageOp imageOp = new ConvolveOp(kernel);
//					g.drawImage(blurV, imageOp, 0, 0);

					g.drawImage(apply(raw, kernel), 0, 0, null);
				} g.dispose();
				
				BufferedImage fnl = new BufferedImage(raw.getWidth(), raw.getHeight(), BufferedImage.TYPE_INT_ARGB);
				{
					for(int x = 0; x < fnl.getWidth(); x ++) {
					for(int y = 0; y < fnl.getHeight(); y ++) {
						float xVal = (sobelX.getRGB(x, y) & 0xFF) - 127;
						float yVal = (sobelY.getRGB(x, y) & 0xFF) - 127;
						
						float value = (float) Math.sqrt(xVal * xVal + yVal * yVal);
						value = Math.max(-128, Math.min(value, 128));
						
						float direction = (float) Math.toDegrees(Math.atan(yVal/ xVal));
						direction = (direction + 180) / 360;
						
//						System.out.println(xVal + "," + yVal);
						
//						value = yVal;
//						value += 128; 
						value /= 128;
						int rgb = Color.getHSBColor(direction, 1, value).getRGB();
//						int rgb = 0xFF << 24 | (int)(value * 255) << 16 | (int)(value * 255) << 8 | (int)(value * 255);
						fnl.setRGB(x, y, rgb);
					}}
				}
				
				og.drawImage(blurV, 0, 0, null);
			}
			
			private BufferedImage apply(BufferedImage src, Kernel kernel) {
				float[][] data = new float[src.getWidth()][src.getHeight()];
				
				for(int x = 0; x < src.getWidth(); x ++) {
				for(int y = 0; y < src.getHeight(); y ++) {
					float[] kData = kernel.getKernelData(null);
					
					for(int i = -kernel.getWidth()/2;  i <= kernel.getWidth()/2;  i ++) {
					for(int j = -kernel.getHeight()/2; j <= kernel.getHeight()/2; j ++) {
						int ax = Math.max(0, Math.min(x + i, src.getWidth() - 1));
						int ay = Math.max(0, Math.min(y + j, src.getHeight() - 1));
						
						int value = src.getRGB(ax, ay) & 0xFF;
						float mult = kData[i + kernel.getWidth()/2 + (j + kernel.getHeight()/2) * kernel.getWidth()];
						data[x][y] += value * mult;
					}}
				}}
				
				float max = 0, min = data[0][0];
				for(int x = 0; x < src.getWidth(); x ++) {
				for(int y = 0; y < src.getHeight(); y ++) {
					if(data[x][y] > max) max = data[x][y];
					if(data[x][y] < min) min = data[x][y];
				}}

				BufferedImage dst = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
				
				float ratio = 255 / (max - min);
				System.out.println(min + ", " + max + " -> " + ratio);
				for(int x = 0; x < src.getWidth(); x ++) {
				for(int y = 0; y < src.getHeight(); y ++) {
					int val = (int) ((data[x][y] - min) * ratio);
					int rgb = 0xFF << 24 | val << 16 | val << 8 | val;
					
					dst.setRGB(x, y, rgb);
				}}

				return dst;
			}
			
			public void draw3(Graphics2D g) {
				String str = "A bc~gf";
				
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, 1000, 1000);
				
				g.setFont(new Font("Times New Roman", Font.PLAIN, 72));
				FontMetrics metrics = g.getFontMetrics();
				Rectangle2D bounds = metrics.getStringBounds(str, g);
				
//				g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
				
				double x = (contentPane.getWidth() - bounds.getWidth()) / 2;
				double y = (contentPane.getHeight() - bounds.getHeight()) / 2;
//				g.translate(x, y - bounds.getY());
				
				FontRenderContext context = new FontRenderContext(g.getTransform(), true, true);
				GlyphVector vector = g.getFont().createGlyphVector(context, str);
				
				new DistanceFieldEffect().draw(g, vector.getGlyphOutline(0, 25, 75));
				
				g.setColor(Color.WHITE);
				g.fill(vector.getGlyphOutline(0, (float)x, (float)y));
			}
			
			public void draw(Graphics2D g) {
				FontMetadata meta = new FontMetadata();
				meta.context = new FontRenderContext(null, false, false);
				meta.font = new Font("Candara", Font.PLAIN, 119); //Times New Roman
				meta.metrics = g.getFontMetrics(meta.font);
				meta.upscale = 5;
				meta.smeer = 10;
				
//				g.drawImage(SymbolFactory.createSymbol(' ', meta).image, 100, 0, null);
				
				SymbolSheetMetadata sheetMeta = new SymbolSheetMetadata();
				sheetMeta.font = meta;
				sheetMeta.useMultiChannel = true;
				sheetMeta.width = sheetMeta.height = 512;
				
				ArrayList<Symbol> chars = new ArrayList<>();
				for(char i : "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890\"!`?'.,;:()[]{}<>|/@\\^$-%+=#_&~*".toCharArray()) chars.add(SymbolFactory.createSymbol(i, meta));
				
				SymbolSheet sheet = SymbolSheetPacker.pack(sheetMeta, chars);

				for(BufferedImage image : sheet.pages) {
					g.drawImage(image, 0, 0, null);
					g.setColor(Color.RED);
					g.drawRect(0, 0, image.getWidth() - 1, image.getHeight() - 1);
					g.translate(sheetMeta.width, 0);
				}
				
//				g.setFont(meta.font);
//				FontRenderContext context = new FontRenderContext(g.getTransform(), true, true);
//				GlyphVector vector = g.getFont().createGlyphVector(context, "a");
//				new DistanceFieldEffect().draw(g, vector.getGlyphOutline(0, 25, 75));
			}
			
			public void drawa(Graphics2D g) {
				String str = "A bc~gf";
				
				g.setFont(new Font("Times New Roman", Font.PLAIN, 256));
				FontMetrics metrics = g.getFontMetrics();
				Rectangle2D bounds = metrics.getStringBounds(str, g);
				LineMetrics line = metrics.getLineMetrics(str, g);
				
				g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
				
				double x = (contentPane.getWidth() - bounds.getWidth()) / 2;
				double y = (contentPane.getHeight() - bounds.getHeight()) / 2;
				g.translate(x, y - bounds.getY());
				
				FontRenderContext context = new FontRenderContext(g.getTransform(), true, true);
				GlyphVector vector = g.getFont().createGlyphVector(context, str);

				g.setColor(new Color(0, 0, 0, 1f));
//				g.drawString(str, 0, 0);
				g.fill(vector.getGlyphOutline(0));
				
				float shift = 0;
				for(int i = 0; i < str.length(); i ++) {
					g.setColor(new Color(1, .5f, 1, 1f));
					g.setStroke(new BasicStroke(2));
					g.draw(vector.getGlyphOutline(i));
					
					g.setColor(new Color(1, 0, 0, 1f));
					GeneralPath path = (GeneralPath) vector.getGlyphOutline(i);
					PathIterator iterator = path.getPathIterator(null);
					while(!iterator.isDone()) {
						float[] points = new float[4];
						iterator.currentSegment(points);
						iterator.next();
						
						g.drawRect((int) points[0] - 1, (int) points[1] - 1, 3, 3);
					}
					
					GlyphMetrics glyphMetrics = vector.getGlyphMetrics(i);

					g.translate(shift, 0);
					g.setColor(new Color(0, 1, 1, 1f));
					Rectangle2D b = glyphMetrics.getBounds2D();
					g.draw(b);
					
					g.translate(-shift, 0);
//					g.setColor(new Color(1, 0, 0, 1f));
//					g.setStroke(new BasicStroke(1));
//					g.draw(vector.getGlyphOutline(i).getBounds2D());
					
					shift += glyphMetrics.getAdvanceX();
				}
				
				double w1 = g.getFont().createGlyphVector(context, "ACB").getVisualBounds().getWidth();
				double w2 = g.getFont().createGlyphVector(context, "AB").getVisualBounds().getWidth();
				
				System.out.println(w1 - w2);
				System.out.println(metrics.getWidths()['C']);
				
				// Bounds
				g.setStroke(new BasicStroke(1));
				g.setColor(new Color(1, 0, 0, 1f));
				g.draw(bounds);
				
				// BaseLine
				g.setColor(new Color(0, 0, 1, 1f));
				g.drawLine(0, 0, (int)bounds.getWidth(), 0);
				
				// Ascent
				g.setStroke(new BasicStroke(2));
				g.setColor(new Color(0, 1, 0, 1f));
				g.drawLine(0, (int) -line.getAscent(), 0, 0);
				g.drawLine(0, (int) -line.getAscent(), (int)bounds.getWidth(), (int) -line.getAscent());
				
				// Descent
				g.setColor(new Color(1, 1, 0, 1f));
				g.drawLine((int)bounds.getWidth(), 0, (int)bounds.getWidth(), (int) line.getDescent());
				g.drawLine(0, (int) line.getDescent(), (int)bounds.getWidth(), (int) line.getDescent());
			}
		 */
		}

	public void windowClosing(WindowEvent e) { windowClosed(e); }

	public void windowIconified(WindowEvent e) { }
	public void windowDeiconified(WindowEvent e) { }
	public void windowActivated(WindowEvent e) { }
	public void windowDeactivated(WindowEvent e) { }
	public void windowOpened(WindowEvent e) { }
}
