    static class FabPersonne {
        static FabPersonne _singleton = null; 
        Connection conn;
        public FabPersonne() {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost/java?user=java&password=java");
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        public static FabPersonne getInstance() {
            if (_singleton == null) {
                _singleton = new FabPersonne();
            } 
            return _singleton;            
        }
        public Personne getPersonne(int id) throws SQLException {
                PreparedStatement stmt = conn.prepareStatement("SELECT nom, prenom FROM personnes WHERE id = ?");
                stmt.setInt(1, id);
                ResultSet res = stmt.executeQuery();
                res.next();
                Personne p = new Personne(res.getString(1), res.getString(2));
                return(p);
        }
        public List<Personne> getNmoins1(int id) throws SQLException {
                List<Personne> lp = new ArrayLits<Personne>();
                PreparedStatement stmt = conn.prepareStatement("SELECT nom, prenom FROM personnes WHERE np1 = ?");
                stmt.setInt(1, id);
                ResultSet res = stmt.executeQuery();
                while (res.next()) {
                    lp.add(new Personne(res.getString(1), res.getString(2)));
                }
                return(lp);
         }
        public Evaluation getEval(int id) {
                PreparedStatement stmt = conn.prepareStatement("SELECT note1, note2, appreciation FROM evaluations WHERE pers_id = ?");
                stmt.setInt(1, id);
                ResultSet res = stmt.executeQuery();
                res.next();
                Evaluation e = new Evaluation(res.getInt(1), res.getInt(2), res.getString(3));
                return(e);
        }
        public Personne evaluer(int id, int note1, int note2, String appreciation) {
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO evaluations (pers_id, note1, note2, appreciation) VALUES(?, ?, ?, ?)");
                stmt.clearParameters();
                stmt.setInt(1, id);
                stmt.setInt(2, note1);
                stmt.setInt(3, note2);
                stmt.setString(4, appreciation);
                stmt.execute();
        }
    }
