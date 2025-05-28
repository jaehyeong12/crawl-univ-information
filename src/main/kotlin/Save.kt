import java.sql.DriverManager

class Save {
    fun invoke(subjects: List<OpenSubject> ){
        val url = "jdbc:oracle:thin:@localhost:1521/XEPDB1"
        val user = "system"      // 사용자명
        val password = "991105" // 비밀번호

        val sql = """
        INSERT INTO SYSTEM.OPEN_SUBJECTS (
            subject_code, subject_name, class_no, credit_type, subject_type,
            competency, liberal_large, liberal_medium, liberal_small,
            online_rate, professor, affiliation, college, department, major,
            day_night, open_grade, credit, lecture_hours, lab_required,
            eval_type, pass_course, intensive_course, schedule, note
        ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    """.trimIndent()

        DriverManager.getConnection(url, user, password).use { conn ->
            conn.autoCommit = false
            conn.prepareStatement(sql).use { pstmt ->
                for (s in subjects) {
                    pstmt.setString(1, s.subjectCode)
                    pstmt.setString(2, s.subjectName)
                    pstmt.setString(3, s.classNo)
                    pstmt.setString(4, s.creditType)
                    pstmt.setString(5, s.subjectType)
                    pstmt.setString(6, s.competency)
                    pstmt.setString(7, s.liberalLarge)
                    pstmt.setString(8, s.liberalMedium)
                    pstmt.setString(9, s.liberalSmall)
                    pstmt.setObject(10, s.onlineRate)
                    pstmt.setString(11, s.professor)
                    pstmt.setString(12, s.affiliation)
                    pstmt.setString(13, s.college)
                    pstmt.setString(14, s.department)
                    pstmt.setString(15, s.major)
                    pstmt.setString(16, s.dayNight)
                    pstmt.setString(17, s.openGrade)
                    pstmt.setObject(18, s.credit)
                    pstmt.setObject(19, s.lectureHours)
                    pstmt.setObject(20, s.labRequired)
                    pstmt.setString(21, s.evalType)
                    pstmt.setObject(22, s.passCourse)
                    pstmt.setObject(23, s.intensiveCourse)
                    pstmt.setString(24, s.schedule)
                    pstmt.setString(25, s.note)

                    pstmt.addBatch()
                }
                pstmt.executeBatch()
            }
            conn.commit()
        }
    }
}
