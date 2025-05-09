    import java.sql.DriverManager; // Loads the JDBC driver
    import java.sql.SQLException; // Handles SQL exceptions
    import java.sql.Connection; // Manages database connection
    import java.sql.Statement; // Executes SQL queries
    import java.sql.ResultSet; // Stores query results
    import java.util.Scanner; // Reads user input


    public class Main {

        private static final String url = "db.url";
        private static final String user = "db.userName";
        private static final String password = "db.password";

        public static void main(String[] args) throws ClassNotFoundException, SQLException {

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }

            try {
                Connection connection = DriverManager.getConnection(url, user, password);
                while (true) {
                    System.out.println("Hotel Management System");
                    Scanner sc = new Scanner(System.in);
                    System.out.println("""
                            1. Reserve Room
                            2. View Reservations
                            3. Get Room Number
                            4. Update Reservations
                            5. Delete Reservations
                            0. Exit
                            Choose an Option""");
                    int choice = sc.nextInt();

                    switch (choice) {
                        case 1 -> reserveRoom(connection, sc);
                        case 2 -> viewReservation(connection, sc);
                        case 3 -> getRoomNumber(connection, sc);
                        case 4 -> updateReservation(connection, sc);
                        case 5 -> deleteReservation(connection, sc);
                        case 0 -> {
                            exit();
                            sc.close();
                            connection.close();
                            return;
                        }
                        default -> System.out.println("Invalid choice. Try again.");
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());

            }
        }

        private static void reserveRoom(Connection connection, Scanner sc) {
            System.out.print("Enter Guest Name:");
            String guest_name = sc.next();
            sc.nextLine();

            System.out.print("Enter Room Number:");
            int room_number = sc.nextInt();

            System.out.print("Enter Contact Number:");
            String contact_number = sc.next();

            String insertQuery = "INSERT INTO room_reservation(guest_Name, room_number, contact_number)" +
                                 "VALUES ('" + guest_name + "'," + room_number + ", '" + contact_number + "')";
            try {
                Statement statement = connection.createStatement();
                int affectedRows = statement.executeUpdate(insertQuery);

                if (affectedRows > 0) {
                    System.out.println("Reservation Successful!");
                } else {
                    System.out.println("Reservation Failed.");
                }
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        private static void viewReservation(Connection connection, Scanner sc) throws SQLException {
            String retrievedQuery = "SELECT * FROM room_reservation";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(retrievedQuery);

            System.out.println("Current Reservations:");
            System.out.println("""
                    +-----------------+----------------------+-------------+----------------+-------------------------+
                    | Reservation ID  |      Guest Name      | Room Number | Contact Number | Reservation Date        |
                    +-----------------+----------------------+-------------+----------------+-------------------------+""");

            while (resultSet.next()) {
                int reservation_id = resultSet.getInt("reservation_id");
                String guest_name = resultSet.getString("guest_name");
                int room_number = resultSet.getInt("room_number");
                String contact_number = resultSet.getString("contact_number");
                String reservation_date = String.valueOf(resultSet.getTimestamp("reservation_date"));

                System.out.printf("| %-15s | %-20s | %-11s | %-14s | %-23s |\n",
                        reservation_id, guest_name, room_number, contact_number, reservation_date);

            }
            System.out.println("+-----------------+----------------------+-------------+----------------+-------------------------+");

            statement.close();
            resultSet.close();
        }

        private static void getRoomNumber(Connection connection, Scanner sc){
            try {
                System.out.println("Enter Reservation ID");
                int reserveID = sc.nextInt();
                System.out.println("Enter Guest Name");
                String guestName = sc.next();

                String retrievedQuery = String.format("SELECT room_number FROM room_reservation WHERE reservation_id = %d AND guest_name = '%s'",
                        reserveID, guestName);

                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(retrievedQuery);

                if (resultSet.next()) {
                    int roomNumber = resultSet.getInt("room_number");
                    System.out.printf("Room number for Reservation ID: %d and Guest Name: %s is %d\n", reserveID, guestName, roomNumber);
                } else {
                    System.out.println("Reservation not found for the given ID and guest name.");
                }

                statement.close();
                resultSet.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        private static void updateReservation(Connection connection, Scanner sc) {
            System.out.print("Enter Reservation ID to Update: ");
            int reserveID = sc.nextInt();
            System.out.print("Enter name: ");
            String updateName = sc.next();
            System.out.print("Enter roomNo: ");
            int updateRN = sc.nextInt();
            System.out.print("Enter phno: ");
            String updatePhNo = sc.next();
            try {
                String updateQuery = String.format("UPDATE room_reservation SET guest_name = '%s', room_number = '%d', contact_number = '%s' WHERE reservation_id = %d",
                        updateName, updateRN, updatePhNo, reserveID);
                Statement statement = connection.createStatement();
                int affectedRow = statement.executeUpdate(updateQuery);

                if (affectedRow > 0) {
                    System.out.println("Reservation updated Successfully!");
                } else {
                    System.out.println("Reservation update failed.");
                }

                statement.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        private static void deleteReservation(Connection connection, Scanner sc) throws SQLException {
            System.out.print("Enter Reservation ID to delete: ");
            int reserveID = sc.nextInt();
            String deleteQuery = String.format("DELETE FROM room_reservation WHERE reservation_id = %d", reserveID);
            try {
                Statement statement = connection.createStatement();
                int affectedRows = statement.executeUpdate(deleteQuery);

                if (affectedRows > 0) {
                    System.out.println("Reservation Deleted Successfully!");
                } else {
                    System.out.println("Reservation Deletion failed.");
                }

                statement.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        public static void exit() {
            System.out.print("Exiting System");
            int i = 5;
            try {
                while (i != 0) {
                    System.out.print(".");
                    Thread.sleep(200);
                    i--;
                }
            } catch (InterruptedException e) {
                System.err.println("Thread interrupted during exit: " + e.getMessage());
                Thread.currentThread().interrupt(); // Preserve interrupted status
            }
            System.out.println();
            System.out.println("Thank you for using Hotel Reservation System!!!");
        }

    }
