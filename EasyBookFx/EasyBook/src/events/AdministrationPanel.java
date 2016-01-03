package events;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.mysql.jdbc.Statement;

import application.Book;
import application.Main;
import application.Offer;
import application.Room;
import database.Conn;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AdministrationPanel implements Initializable {

	public Connection conn = Conn.connect();

	@FXML
	private TableColumn<Book, String> check_in_clmn;
	@FXML
	private TableColumn<Book, String> paid_clmn;
	@FXML
	private TableColumn<Book, String> name_clmn;
	@FXML
	private TableColumn<Book, String> surname_clmn;
	@FXML
	private TableColumn<Book, String> check_out_clmn;
	@FXML
	private TableColumn<Book, String> tel_clmn;
	@FXML
	private TableColumn<Book, String> email_clmn;
	@FXML
	private TableColumn<Book, String> idnum_clmn;
	@FXML
	private TableColumn<Book, String> persons_clmn;
	@FXML
	private TableColumn<Book, String> code_clmn;
	@FXML
	private TableColumn<Book, String> room_clmn;

	@FXML
	private TableColumn<Room, Integer> room_name_col;
	@FXML
	private TableColumn<Room, String> type_col;
	@FXML
	private TableColumn<Room, Integer> single_beds_col;
	@FXML
	private TableColumn<Room, Integer> double_beds_col;
	@FXML
	private TableColumn<Room, Float> cost_col;

	@FXML
	private TableColumn<Offer, String> offer_name_col;
	@FXML
	private TableColumn<Offer, String> offer_req_days_col;
	@FXML
	private TableColumn<Offer, String> offers_type_stand_col;
	@FXML
	private TableColumn<Offer, String> offers_type_comf_col;
	@FXML
	private TableColumn<Offer, String> offers_type_suite_col;
	@FXML
	private TableColumn<Offer, String> offers_beds_one_col;
	@FXML
	private TableColumn<Offer, String> offers_beds_two_col;
	@FXML
	private TableColumn<Offer, String> offers_beds_three_col;
	@FXML
	private TableColumn<Offer, String> offers_beds_fplus_col;
	@FXML
	private TableColumn<Offer, String> offers_disc_per_col;
	@FXML
	private TableColumn<Offer, String> offers_disc_am_col;
	@FXML
	private TableColumn<Offer, String> offers_valid_from_col;
	@FXML
	private TableColumn<Offer, String> offers_valid_until_col;

	@FXML
	private ToggleGroup categoryTypesRooms;
	@FXML
	private ToggleGroup categoryOffers;
	@FXML
	private ToggleGroup categoryRadioDatesOffers;
	@FXML
	private ToggleGroup categoryBookings;
	@FXML
	private ToggleGroup categoryRadioTypeOffers;
	@FXML
	private ToggleGroup categoryTypeOptions;
	@FXML
	private ToggleGroup categoryIncomeBooksStatistics;
	@FXML
	private ToggleGroup categoryBedRooms;


	@FXML
	private TextField searchBookText;
	@FXML
	private TextField searchRoomText;
	@FXML
	private TextField offer_name_text;
	@FXML
	private TextField offer_req_days_text;
	@FXML
	private TextField offer_dis_per_text;
	@FXML
	private TextField offer_dis_am_text;
	
	@FXML
	private DatePicker offer_valid_from_date;
	@FXML
	private DatePicker offer_valid_until_date;
	
	@FXML
	private RadioButton offer_dis_per_radio;
	@FXML
	private RadioButton offer_dis_am_radio;
	
	@FXML
	private CheckBox offer_type_stand_check;
	@FXML
	private CheckBox offer_type_comf_check;
	@FXML
	private CheckBox offer_type_suite_check;
	@FXML
	private CheckBox offer_one_bed_check;
	@FXML
	private CheckBox offer_two_beds_check;
	@FXML
	private CheckBox offer_three_beds_check;
	@FXML
	private CheckBox offer_fplus_beds_check;
	
	@FXML
	private TextArea offer_desc_text;

	@FXML
	private TableView<Book> bookingsTable;
	@FXML
	private TableView<Room> roomsTable;
	@FXML
	private TableView<Offer> offersTable;
	
	@FXML
	private ToggleButton allBookings;
	@FXML
	private ToggleButton completedBookings;
	@FXML
	private ToggleButton comingSoonBookings;
	@FXML
	private ToggleButton runningBookings;
	@FXML
	private ToggleButton allRooms;
	@FXML
	private ToggleButton standardRooms;
	@FXML
	private ToggleButton comfortRooms;
	@FXML
	private ToggleButton suiteRooms;
	@FXML
	private ToggleButton rooms_1bed;
	@FXML
	private ToggleButton rooms_2beds;
	@FXML
	private ToggleButton rooms_3beds;
	@FXML
	private ToggleButton rooms_4plusbeds;

	private ObservableList<Book> bookingList;
	private ObservableList<Room> roomsList;
	private ObservableList<Offer> offersList;
	
	@SuppressWarnings("unused")
	private int o_id_ToEdit;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		showAllBookings(null);
		showAllRooms(null);
		showAllOffers(null);
	}

	/* Bookings TAB */
	public void newBook(ActionEvent event) {
		Main main = new Main();
		main.startNewBookPanel();
	}

	public void showAllBookings(ActionEvent event) {
		showBookingsOnTable("Select * from bookings");
	}
	public void showRunning(ActionEvent event) {
		showBookingsOnTable("Select * from bookings"
				+ " where ( `check_in` >= concat(curdate(), ' ', curtime()) ) "
				+ "AND (`check_out` < concat(curdate(), ' ', curtime()) )");
	}
	public void showComingSoon(ActionEvent event) {
		showBookingsOnTable("Select * from bookings"
				+ " where `check_in` > concat( curdate(), ' ', curtime() )");
	}
	public void showCompleted(ActionEvent event) {
		showBookingsOnTable("Select * from bookings"
				+ " where `check_out` <= concat( curdate(), ' ', curtime() )");
	}

	public void searchBookFromText(ActionEvent event) {

		String textSearch = searchBookText.getText();

		if (! textSearch.isEmpty() ) {

			deselectBookingsToggle();



			String query = "SELECT * FROM bookings "
					+ "WHERE `b_id` LIKE '%" + textSearch + "%'	"
					+ "OR	`code`  LIKE '%" + textSearch + "%'	"
					+ "OR	`name` LIKE '%" + textSearch + "%'	"
					+ "OR	`sname` LIKE '%" + textSearch + "%'	"
					+ "OR 	`tel` LIKE '%" + textSearch + "%'	"
					+ "OR	`email` LIKE '%" + textSearch + "%'	";

			showBookingsOnTable(query);


		}
	}
	public void showBookingsOnTable(String query) {

		code_clmn.setCellValueFactory(new PropertyValueFactory<Book, String>("code") );
		name_clmn.setCellValueFactory(new PropertyValueFactory<Book, String>("name") );
		surname_clmn.setCellValueFactory(new PropertyValueFactory<Book, String>("sname") );
		check_in_clmn.setCellValueFactory(new PropertyValueFactory<Book, String>("check_in") );
		check_out_clmn.setCellValueFactory(new PropertyValueFactory<Book, String>("check_out") );
		tel_clmn.setCellValueFactory(new PropertyValueFactory<Book, String>("tel") );
		email_clmn.setCellValueFactory(new PropertyValueFactory<Book, String>("email") );
		idnum_clmn.setCellValueFactory(new PropertyValueFactory<Book, String>("idnum") );
		room_clmn.setCellValueFactory(new PropertyValueFactory<Book, String>("name") );
		persons_clmn.setCellValueFactory(new PropertyValueFactory<Book, String>("paid") );
		paid_clmn.setCellValueFactory(new PropertyValueFactory<Book, String>("paid") );

		bookingList = getBookingsTableData(query);
		bookingsTable.setItems( bookingList );


	}
	private ObservableList<Book> getBookingsTableData(String query) {

		List<Book> list = new ArrayList<Book>();

		try {
			Statement stmt = (Statement) conn.createStatement();

			ResultSet rs = stmt.executeQuery( query );

			while (rs.next()) {
				list.add( new Book(
						rs.getInt("b_id"), 
						rs.getString("code"), 
						rs.getString("check_in"),
						rs.getString("check_out"),
						rs.getString("name"),
						rs.getString("sname"),
						rs.getString("tel"),
						rs.getString("email"),
						rs.getString("idnum"),
						rs.getString("payment_method"),
						rs.getFloat("total_cost"),
						rs.getString("paid"),
						rs.getFloat("money_received"),
						rs.getString("status") ) ); 
			}
		} catch (SQLException e )	{ e.printStackTrace();
		} catch (ParseException e)	{ e.printStackTrace();	}

		ObservableList<Book> data = FXCollections.observableList(list);

		return data;
	}
	private void deselectBookingsToggle(){
		allBookings.setSelected(false);
		completedBookings.setSelected(false);
		runningBookings.setSelected(false);
		comingSoonBookings.setSelected(false);
	}

	/* Rooms TAB */
	public void showAllRooms(ActionEvent event) {
		deselectRoomsBedsToggle();
		showRoomsOnTable("Select * from rooms");
	}
	public void showStandardRooms(ActionEvent event) {
		deselectRoomsBedsToggle();
		showRoomsOnTable("Select * from rooms where `room_type`='Standard'");
	}
	public void showComfortRooms(ActionEvent event) {
		deselectRoomsBedsToggle();
		showRoomsOnTable("Select * from rooms where `room_type`='Comfort'");
	}
	public void showSuiteRooms(ActionEvent event) {
		deselectRoomsBedsToggle();
		showRoomsOnTable("Select * from rooms where `room_type`='Suite'");
	}

	public void showRooms1Bed(ActionEvent event) {
		deselectRoomsTypeToggle();
		showRoomsOnTable("Select * from rooms where `single_beds` + `double_beds` = 1");
	}
	public void showRooms2Beds(ActionEvent event) {
		deselectRoomsTypeToggle();
		showRoomsOnTable("Select * from rooms where `single_beds` + `double_beds` = 2");
	}
	public void showRooms3Beds(ActionEvent event) {
		deselectRoomsTypeToggle();
		showRoomsOnTable("Select * from rooms where `single_beds` + `double_beds` = 3");
	}
	public void showRooms4plusBeds(ActionEvent event) {
		deselectRoomsTypeToggle();
		showRoomsOnTable("Select * from rooms where `single_beds` + `double_beds` >= 4");
	}

	public void searchRoomFromText(ActionEvent event) {

		deselectRoomsTypeToggle();
		deselectRoomsBedsToggle();

		String textSearch = searchRoomText.getText();

		if (! textSearch.isEmpty() ) {

			String query = "SELECT * FROM `rooms` WHERE "
					+ "`room_name` LIKE '%" + textSearch + "%'";

			showRoomsOnTable(query);
		}
	}
	public void showRoomsOnTable(String query) {

		room_name_col.setCellValueFactory(new PropertyValueFactory<Room, Integer>("room_name") );
		type_col.setCellValueFactory(new PropertyValueFactory<Room, String>("room_type") );
		single_beds_col.setCellValueFactory(new PropertyValueFactory<Room, Integer>("single_beds") );
		double_beds_col.setCellValueFactory(new PropertyValueFactory<Room, Integer>("double_beds") );
		cost_col.setCellValueFactory(new PropertyValueFactory<Room, Float>("cost") );

		roomsList = getRoomsTableData(query);
		roomsTable.setItems( roomsList );

	}
	private ObservableList<Room> getRoomsTableData(String query) {

		List<Room> list = new ArrayList<Room>();

		try {
			Statement stmt = (Statement) conn.createStatement();

			ResultSet rs = stmt.executeQuery( query );

			while ( rs.next()) {

				list.add( new Room(
						rs.getInt("room_id"), 
						rs.getString("room_name"), 
						rs.getString("room_type"),
						rs.getInt("single_beds"),
						rs.getInt("double_beds"),
						rs.getFloat("cost") ) ); 
			}
		} catch (SQLException e) { e.printStackTrace();
		}

		ObservableList<Room> data = FXCollections.observableList(list);

		return data;
	}
	private void deselectRoomsTypeToggle(){
		allRooms.setSelected(false);
		standardRooms.setSelected(false);
		comfortRooms.setSelected(false);
		suiteRooms.setSelected(false);
	}
	private void deselectRoomsBedsToggle(){
		rooms_1bed.setSelected(false);
		rooms_2beds.setSelected(false);
		rooms_3beds.setSelected(false);
		rooms_4plusbeds.setSelected(false);
	}


	/* Offers TAB */
	public void showAllOffers(ActionEvent event) {
		showOffersOnTable("Select * from offers");
	}	
	public void showOffersOnTable(String query) {

		offer_name_col.setCellValueFactory(new PropertyValueFactory<Offer, String>("name") );
		offer_req_days_col.setCellValueFactory(new PropertyValueFactory<Offer, String>("req_days") );
		offers_type_stand_col.setCellValueFactory(new PropertyValueFactory<Offer, String>("type_stand") );
		offers_type_comf_col.setCellValueFactory(new PropertyValueFactory<Offer, String>("type_comf") );
		offers_type_suite_col.setCellValueFactory(new PropertyValueFactory<Offer, String>("type_suite") );
		offers_beds_one_col.setCellValueFactory(new PropertyValueFactory<Offer, String>("one_bed") );
		offers_beds_two_col.setCellValueFactory(new PropertyValueFactory<Offer, String>("two_beds") );
		offers_beds_three_col.setCellValueFactory(new PropertyValueFactory<Offer, String>("three_beds") );
		offers_beds_fplus_col.setCellValueFactory(new PropertyValueFactory<Offer, String>("fplus_beds") );
		offers_disc_per_col.setCellValueFactory(new PropertyValueFactory<Offer, String>("discount_percentage") );
		offers_disc_am_col.setCellValueFactory(new PropertyValueFactory<Offer, String>("discount_amount") );
		offers_valid_from_col.setCellValueFactory(new PropertyValueFactory<Offer, String>("valid_from") );
		offers_valid_until_col.setCellValueFactory(new PropertyValueFactory<Offer, String>("valid_until") );

		offersList = getOffersTableData(query);
		offersTable.setItems( offersList );

	}
	private ObservableList<Offer> getOffersTableData(String query) {

		List<Offer> list = new ArrayList<Offer>();

		try {
			Statement stmt = (Statement) conn.createStatement();

			ResultSet rs = stmt.executeQuery( query );

			while ( rs.next()) {

				list.add( new Offer(
						rs.getInt("o_id"),
						rs.getString("name"),
						rs.getString("valid_from"),
						rs.getString("valid_until"),
						rs.getInt("required_days"),
						rs.getInt("one_bed"),
						rs.getInt("two_beds"),
						rs.getInt("three_beds"),
						rs.getInt("fplus_beds"),
						rs.getInt("type_stand"),
						rs.getInt("type_comf"),
						rs.getInt("type_suite"),
						rs.getInt("discount_amount"),
						rs.getInt("discount_percentage") ) ); 
			}
		} catch (SQLException e) { e.printStackTrace();
		}

		ObservableList<Offer> data = FXCollections.observableList(list);

		return data;
	}
	public void editOffer (ActionEvent event) throws ParseException {
		Offer offerToEdit = offersTable.getSelectionModel().getSelectedItem();
		
		o_id_ToEdit = offerToEdit.getO_id();
		
		offer_name_text.setText(String.valueOf( offerToEdit.getName() ));
		
		offer_req_days_text.setText(String.valueOf(offerToEdit.getReq_days() ) );
		
		offer_valid_from_date.setValue( (LocalDate) offerToEdit.getValid_from_edit() );
		
		offer_valid_until_date.setValue( (LocalDate) offerToEdit.getValid_until_edit() );
		
		if ( offerToEdit.getDiscount_amount() != 0 ) {
			offer_dis_am_radio.setSelected(true);
			offer_dis_am_text.setText(String.valueOf( offerToEdit.getDiscount_amount() ));
		} else {
			offer_dis_per_radio.setSelected(true);
			offer_dis_per_text.setText(String.valueOf( offerToEdit.getDiscount_percentage() ));
		}
		
		if ( offerToEdit.getType_stand_edit() == 1 ) {
			offer_type_stand_check.setSelected(true);
		} else {
			offer_type_stand_check.setSelected(false);
		}
		
		if ( offerToEdit.getType_comf_edit() == 1 ) {
			offer_type_comf_check.setSelected(true);
		} else {
			offer_type_comf_check.setSelected(false);
		}
		
		if ( offerToEdit.getType_suite_edit() == 1 ) {
			offer_type_suite_check.setSelected(true);
		} else {
			offer_type_suite_check.setSelected(false);
		}
		
		if ( offerToEdit.getOne_bed_edit() == 1 ) {
			offer_one_bed_check.setSelected(true);
		} else {
			offer_one_bed_check.setSelected(false);
		}
		
		if ( offerToEdit.getTwo_beds_edit() == 1 ) {
			offer_two_beds_check.setSelected(true);
		} else {
			offer_two_beds_check.setSelected(false);
		}
		
		if ( offerToEdit.getThree_beds_edit() == 1 ) {
			offer_three_beds_check.setSelected(true);
		} else {
			offer_three_beds_check.setSelected(false);
		}
		
		if ( offerToEdit.getFplus_beds_edit() == 1 ) {
			offer_fplus_beds_check.setSelected(true);
		} else {
			offer_fplus_beds_check.setSelected(false);
		}
		
		//offer_desc_text
		
	}
}