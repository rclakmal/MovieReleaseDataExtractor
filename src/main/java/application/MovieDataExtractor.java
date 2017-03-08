package application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import info.movito.themoviedbapi.TmdbFind;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.FindResults;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.ReleaseInfo;
import info.movito.xrelto.XrelApiCaller;
import info.movito.xrelto.XrelMovieAssetExtP2PRelease;
import info.movito.xrelto.XrelMovieAssetExtP2PReleaseFind;
import info.movito.xrelto.XrelMovieAssetExtRelease;
import info.movito.xrelto.XrelMovieAssetExtReleaseFind;

public class MovieDataExtractor {

	private JFrame frmMovieDataExtractor;
	private JTextField imdbText;
	private List<XrelMovieAssetExtRelease> extInfoReleases;
	private List<XrelMovieAssetExtP2PRelease> extInfoP2PReleases;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MovieDataExtractor window = new MovieDataExtractor();
					window.frmMovieDataExtractor.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MovieDataExtractor() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMovieDataExtractor = new JFrame();
		frmMovieDataExtractor.setTitle("ClueLab Movie Data Extractor");
		frmMovieDataExtractor.setBounds(100, 100, 539, 429);
		frmMovieDataExtractor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmMovieDataExtractor.getContentPane().setLayout(new BorderLayout());
		frmMovieDataExtractor.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		final JPanel movieDBPanel = new JPanel();
		tabbedPane.addTab("Via IMDB ID", null, movieDBPanel, null);
		movieDBPanel.setLayout(null);

		JLabel lblImdbId = new JLabel("IMDB ID");
		lblImdbId.setBounds(78, 17, 50, 16);
		movieDBPanel.add(lblImdbId);

		imdbText = new JTextField();
		imdbText.setBounds(128, 11, 205, 29);
		movieDBPanel.add(imdbText);
		imdbText.setColumns(10);

		final JPanel containerTMDB = new JPanel();
		containerTMDB.setBounds(6, 53, 506, 317);
		movieDBPanel.add(containerTMDB);
		containerTMDB.setLayout(null);

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Via Manual Link", null, panel_1, null);

		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				containerTMDB.removeAll();
				TestMovieFetch testMovieFetch = new TestMovieFetch();
				FindResults result = testMovieFetch.tmdb.getFind().find(imdbText.getText().trim(),
						TmdbFind.ExternalSource.imdb_id, null);
				MovieDb movie = testMovieFetch.tmdb.getMovies().getMovie(result.getMovieResults().get(0).getId(),
						AbstractTmdbApiTest.LANGUAGE_ENGLISH, TmdbMovies.MovieMethod.values());
				List<ReleaseInfo> releases = movie.getReleases();
				String germanReleaseDate = "Release Not Found";
				for (ReleaseInfo release : releases) {
					if (release.getCountry().equals("DE")) {
						germanReleaseDate = release.getReleaseDate();
						break;
					}
				}
				XrelApiCaller xrelCaller = new XrelApiCaller(testMovieFetch.tmdb);
				try {
					String extId = xrelCaller.getExtInfoIdFromImdbId(imdbText.getText().trim()).getExtInfoReleases()
							.get(0).getId();
					XrelMovieAssetExtReleaseFind extInfoReleasesFromId = xrelCaller.getExtInfoReleasesFromId(extId);
					int totalPages = extInfoReleasesFromId.getPagination().getTotalPages();
					extInfoReleases = extInfoReleasesFromId.getExtInfoReleases();

					while (extInfoReleasesFromId.getPagination().getCurrentPage() != totalPages) {
						extInfoReleasesFromId = xrelCaller.getExtInfoReleasesFromId(extId,
								extInfoReleasesFromId.getPagination().getCurrentPage());
						extInfoReleases.addAll(extInfoReleasesFromId.getExtInfoReleases());
					}

					XrelMovieAssetExtP2PReleaseFind extP2PInfoReleasesFromId = xrelCaller
							.getExtP2PInfoReleasesFromId(extId);
					int totalP2PPages = extP2PInfoReleasesFromId.getPagination().getTotalPages();
					extInfoP2PReleases = extP2PInfoReleasesFromId.getExtInfoReleases();

					while (extP2PInfoReleasesFromId.getPagination().getCurrentPage() != totalP2PPages) {
						extP2PInfoReleasesFromId = xrelCaller.getExtP2PInfoReleasesFromId(extId,
								extP2PInfoReleasesFromId.getPagination().getCurrentPage());
						extInfoP2PReleases.addAll(extP2PInfoReleasesFromId.getExtInfoReleases());
					}

				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				containerTMDB.add(createMoviePanel(movie.getTitle(), germanReleaseDate, movie.getPosterPath()),
						BorderLayout.CENTER);
				containerTMDB.revalidate();
				containerTMDB.repaint();
				movieDBPanel.repaint();
				frmMovieDataExtractor.repaint();
			}

			private JPanel createMoviePanel(String title, String germanReleaseDate, String posterPath) {
				final JPanel moviePanel = new JPanel();
				moviePanel.setBorder(new LineBorder(new Color(0, 0, 0)));
				moviePanel.setBounds(5, 5, 480, 280);
				movieDBPanel.add(moviePanel);
				moviePanel.setLayout(null);

				final JPanel posterPanel = new JPanel();
				posterPanel.setBounds(10, 3, 156, 154);
				moviePanel.add(posterPanel);

				final JLabel movieInfoLabel = new JLabel("<html><b>------------Movie Info------------</b></html>");
				movieInfoLabel.setForeground(Color.BLACK);
				movieInfoLabel.setBounds(175, 3, 400, 16);
				moviePanel.add(movieInfoLabel);

				final JLabel lblTitle = new JLabel("Movie Release (DE):");
				lblTitle.setBounds(184, 35, 127, 16);
				moviePanel.add(lblTitle);

				final JLabel lblTitle_1 = new JLabel("Title:");
				lblTitle_1.setBounds(184, 20, 50, 16);
				moviePanel.add(lblTitle_1);

				final JLabel lblMovieTitle = new JLabel("Title");
				lblMovieTitle.setForeground(Color.BLUE);
				lblMovieTitle.setBounds(321, 20, 120, 16);
				moviePanel.add(lblMovieTitle);

				final JLabel lblDate = new JLabel("Release Not Found");
				lblDate.setForeground(Color.BLUE);
				lblDate.setBounds(323, 35, 130, 16);
				moviePanel.add(lblDate);

				final JLabel sceneLable = new JLabel("<html><b>----------Scene Releases----------</b></html>");
				sceneLable.setForeground(Color.BLACK);
				sceneLable.setBounds(175, 60, 400, 16);
				moviePanel.add(sceneLable);

				JLabel lblAqualityText = new JLabel("AQuality:");
				lblAqualityText.setBounds(175, 110, 61, 16);
				moviePanel.add(lblAqualityText);

				final JLabel lblAquality = new JLabel("AQuality N/F");
				lblAquality.setForeground(Color.BLUE);
				lblAquality.setBounds(235, 110, 80, 16);
				lblAquality.setVisible(false);
				moviePanel.add(lblAquality);

				JLabel lblVquality = new JLabel("VQuality:");
				lblVquality.setBounds(330, 110, 61, 16);
				moviePanel.add(lblVquality);

				final JLabel lblVQuality = new JLabel("VQuality N/F");
				lblVQuality.setForeground(Color.BLUE);
				lblVQuality.setBounds(389, 110, 80, 16);
				lblVQuality.setVisible(false);
				moviePanel.add(lblVQuality);

				JLabel lblReleasedateText = new JLabel("Release Date:");
				lblReleasedateText.setBounds(203, 130, 85, 16);
				moviePanel.add(lblReleasedateText);

				final JLabel lblReleaseDate = new JLabel("New label");
				lblReleaseDate.setForeground(Color.BLUE);
				lblReleaseDate.setVisible(false);
				lblReleaseDate.setBounds(300, 130, 130, 16);
				moviePanel.add(lblReleaseDate);

				final JTextField searchScene = new JTextField();
				searchScene.setBounds(182, 75, 254, 27);

				searchScene.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						XrelMovieAssetExtRelease searchForOldestGermanRelease = searchReleases(searchScene.getText());
						lblAquality.setText(searchForOldestGermanRelease.getAuditoType());
						lblVQuality.setText(searchForOldestGermanRelease.getVideoType());
						lblReleaseDate.setText(searchForOldestGermanRelease.getDate());

						lblAquality.setVisible(true);
						lblReleaseDate.setVisible(true);
						lblVQuality.setVisible(true);
						moviePanel.repaint();
						moviePanel.revalidate();
						containerTMDB.revalidate();
						containerTMDB.repaint();
						movieDBPanel.repaint();
						frmMovieDataExtractor.repaint();
					}
				});
				moviePanel.add(searchScene);

				final JLabel p2pReleaseLabel = new JLabel("<html><b>------------P2P Releases------------</b></html>");
				p2pReleaseLabel.setForeground(Color.BLACK);
				p2pReleaseLabel.setBounds(175, 150, 400, 16);
				moviePanel.add(p2pReleaseLabel);

				final JTextField searchP2P = new JTextField();
				searchP2P.setBounds(182, 165, 254, 27);

				JLabel p2pDateText = new JLabel("Release Date:");
				p2pDateText.setBounds(175, 200, 85, 16);
				moviePanel.add(p2pDateText);

				final JLabel p2pDate = new JLabel("");
				p2pDate.setBounds(275, 200, 120, 16);
				p2pDate.setForeground(Color.BLUE);
				moviePanel.add(p2pDate);

				JLabel p2pDirNameText = new JLabel("Release Name:");
				p2pDirNameText.setBounds(175, 220, 100, 16);
				moviePanel.add(p2pDirNameText);

				final JTextArea p2pDirName = new JTextArea("");
				p2pDirName.setLineWrap(true);
				p2pDirName.setWrapStyleWord(true);
				p2pDirName.setOpaque(false);
				p2pDirName.setBackground(new Color(0, 0, 0, 0));
				JScrollPane scrollPane = new JScrollPane(p2pDirName);
				scrollPane.getViewport().setOpaque(false);
				scrollPane.setOpaque(false);
				p2pDirName.setBounds(275, 220, 200, 50);
				p2pDirName.setForeground(Color.BLUE);
				moviePanel.add(p2pDirName);

				searchP2P.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						XrelMovieAssetExtP2PRelease searchForOldestGermanRelease = searchP2PReleases(
								searchP2P.getText());
						p2pDirName.setText(searchForOldestGermanRelease.getDirName());
						p2pDate.setText(searchForOldestGermanRelease.getDate());

						lblAquality.setVisible(true);
						lblReleaseDate.setVisible(true);
						lblVQuality.setVisible(true);
						moviePanel.repaint();
						moviePanel.revalidate();
						containerTMDB.revalidate();
						containerTMDB.repaint();
						movieDBPanel.repaint();
						frmMovieDataExtractor.repaint();
					}
				});
				moviePanel.add(searchP2P);

				URL img;
				try {
					img = new URL("https://image.tmdb.org/t/p/w154" + posterPath);
					ImageIcon image = new ImageIcon(img);
					JLabel label = new JLabel("", image, JLabel.CENTER);
					posterPanel.add(label, BorderLayout.CENTER);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				lblMovieTitle.setText(title);
				lblDate.setText(germanReleaseDate);
				return moviePanel;
			}

		});

		btnSearch.setBounds(334, 11, 85, 30);
		movieDBPanel.add(btnSearch);
	}

	private XrelMovieAssetExtRelease searchReleases(String searchKeyWord) {
		XrelMovieAssetExtRelease finalSearchRelease = new XrelMovieAssetExtRelease();
		finalSearchRelease.setTime(System.currentTimeMillis() / 1000L);
		for (XrelMovieAssetExtRelease release : extInfoReleases) {
			if (release.getDirName().toLowerCase().contains(searchKeyWord.toLowerCase())) {
				if (release.getTime() < finalSearchRelease.getTime()) {
					finalSearchRelease = release;
				}
			}
		}
		return finalSearchRelease;
	}

	private XrelMovieAssetExtP2PRelease searchP2PReleases(String searchKeyWord) {
		XrelMovieAssetExtP2PRelease finalSearchRelease = new XrelMovieAssetExtP2PRelease();
		finalSearchRelease.setTime(System.currentTimeMillis() / 1000L);
		for (XrelMovieAssetExtP2PRelease release : extInfoP2PReleases) {
			if (release.getDirName().toLowerCase().contains(searchKeyWord.toLowerCase())) {
				if (release.getTime() < finalSearchRelease.getTime()) {
					finalSearchRelease = release;
				}
			}
		}
		return finalSearchRelease;
	}
}
