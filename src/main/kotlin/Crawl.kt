import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.util.CellReference
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File

data class OpenSubject(
    val subjectCode: String?,
    val subjectName: String?,
    val classNo: String?,
    val creditType: String?,
    val subjectType: String?,
    val competency: String?,
    val liberalLarge: String?,
    val liberalMedium: String?,
    val liberalSmall: String?,
    val onlineRate: Double?,
    val professor: String?,
    val affiliation: String?,
    val college: String?,
    val department: String?,
    val major: String?,
    val dayNight: String?,
    val openGrade: String?,
    val credit: Int?,
    val lectureHours: Int?,
    val labRequired: Int?,
    val evalType: String?,
    val passCourse: Int?,
    val intensiveCourse: Int?,
    val schedule: String?,
    val note: String?
)


class Crawl {

    fun invoke() : List<OpenSubject> {
        val file = File("개설강좌_리스트.xlsx")
        val workbook = XSSFWorkbook(file.inputStream())
        val sheet = workbook.getSheetAt(0) // 첫 번째 시트
        val openSubjectList = mutableListOf<OpenSubject>()

        for (i in 2..sheet.physicalNumberOfRows) {
            val row = sheet.getRow(i) ?: continue
            val onlineRate = when (val cell = row.getCell(10)) {
                null -> 0.0
                else -> when (cell.cellType) {
                    CellType.NUMERIC -> cell.numericCellValue
                    CellType.STRING -> cell.stringCellValue.toDoubleOrNull() ?: 0.0
                    else -> 0.0
                }
            }

            val subject = OpenSubject(
                subjectCode       = row.getCell(CellReference.convertColStringToIndex("B"))?.toString()?.takeIf { it.isNotBlank() },
                subjectName       = row.getCell(CellReference.convertColStringToIndex("C"))?.toString()?.takeIf { it.isNotBlank() },
                classNo           = row.getCell(CellReference.convertColStringToIndex("D"))?.toString()?.takeIf { it.isNotBlank() },
                creditType        = row.getCell(CellReference.convertColStringToIndex("E"))?.toString()?.takeIf { it.isNotBlank() },
                subjectType       = row.getCell(CellReference.convertColStringToIndex("F"))?.toString()?.takeIf { it.isNotBlank() },
                competency        = row.getCell(CellReference.convertColStringToIndex("H"))?.toString()?.takeIf { it.isNotBlank() }, // G 건너뜀
                liberalLarge      = row.getCell(CellReference.convertColStringToIndex("I"))?.toString()?.takeIf { it.isNotBlank() },
                liberalMedium     = row.getCell(CellReference.convertColStringToIndex("J"))?.toString()?.takeIf { it.isNotBlank() },
                liberalSmall      = row.getCell(CellReference.convertColStringToIndex("K"))?.toString()?.takeIf { it.isNotBlank() },
                onlineRate        = row.getCell(CellReference.convertColStringToIndex("L"))?.takeIf { it.cellType == CellType.NUMERIC }?.numericCellValue,
                professor         = row.getCell(CellReference.convertColStringToIndex("M"))?.toString()?.takeIf { it.isNotBlank() },
                affiliation       = row.getCell(CellReference.convertColStringToIndex("N"))?.toString()?.takeIf { it.isNotBlank() },
                college           = row.getCell(CellReference.convertColStringToIndex("O"))?.toString()?.takeIf { it.isNotBlank() },
                department        = row.getCell(CellReference.convertColStringToIndex("P"))?.toString()?.takeIf { it.isNotBlank() },
                major             = row.getCell(CellReference.convertColStringToIndex("Q"))?.toString()?.takeIf { it.isNotBlank() },
                dayNight          = row.getCell(CellReference.convertColStringToIndex("R"))?.toString()?.takeIf { it.isNotBlank() },
                openGrade         = row.getCell(CellReference.convertColStringToIndex("S"))?.toString()?.takeIf { it.isNotBlank() },
                credit            = row.getCell(CellReference.convertColStringToIndex("T"))?.takeIf { it.cellType == CellType.NUMERIC }?.numericCellValue?.toInt(),
                lectureHours      = row.getCell(CellReference.convertColStringToIndex("U"))?.takeIf { it.cellType == CellType.NUMERIC }?.numericCellValue?.toInt(),
                labRequired       = row.getCell(CellReference.convertColStringToIndex("V"))?.takeIf { it.cellType == CellType.NUMERIC }?.numericCellValue?.toInt(),
                evalType          = row.getCell(CellReference.convertColStringToIndex("W"))?.toString()?.takeIf { it.isNotBlank() },
                passCourse        = row.getCell(CellReference.convertColStringToIndex("X"))?.takeIf { it.cellType == CellType.NUMERIC }?.numericCellValue?.toInt(),
                intensiveCourse   = row.getCell(CellReference.convertColStringToIndex("Y"))?.takeIf { it.cellType == CellType.NUMERIC }?.numericCellValue?.toInt(),
                schedule          = row.getCell(CellReference.convertColStringToIndex("Z"))?.toString()?.takeIf { it.isNotBlank() },
                note              = row.getCell(CellReference.convertColStringToIndex("AA"))?.toString()?.takeIf { it.isNotBlank() }
            )


            openSubjectList.add(subject)
        }
        workbook.close()
        return openSubjectList.toList()
    }
}
