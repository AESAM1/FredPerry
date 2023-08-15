/**
*  Business Engine Extension
*/
/****************************************************************************************
Extension Name: EXT017MI.ChgSelection
Type : ExtendM3Transaction
Script Author: Edison Villar
Date: 2023-08-03

Description:
      Perform action or logic similar to OIS017/F when checking and updating sales price record.

Revision History:
Name                    Date             Version          Description of Changes
Edison Villar           2023-08-03       1.0              Initial Version
******************************************************************************************/
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

public class ChgSelection extends ExtendM3Transaction {
  private final MIAPI mi
  private final DatabaseAPI database
  private final ProgramAPI program
  
  public ChgSelection(MIAPI mi, DatabaseAPI database, ProgramAPI program) {
    this.mi = mi
    this.database = database
    this.program = program
  }
  
  Integer inCONO, inFVDT, inFMAB, inTMAB, inFCHC, inTCHC
  String inPRRF, inCUCD, inCUNO, inFITN, inTITN,inFITG, inTITG, inFITC, inTITC, inFITT, inTITT, inFRSP, inTRSP, inSLFR, inSLTO, 
      inFSUN, inTSUN, inFCUN, inTCUN, inFBGR, inTBGR, inFPRC, inTPRC, inFASS, inTASS

  //int fchc, tchc
  public void main() {
    inCONO = program.LDAZD.CONO
    inPRRF = (mi.in.get("PRRF") == null || mi.in.get("PRRF") == 'null')? "": mi.in.get("PRRF")
    inCUCD = (mi.in.get("CUCD") == null || mi.in.get("CUCD") == 'null')? "": mi.in.get("CUCD")
    inCUNO = (mi.in.get("CUNO") == null || mi.in.get("CUNO") == 'null')? "": mi.in.get("CUNO")
    inFVDT = (mi.in.get("FVDT") == null || mi.in.get("FVDT") == 'null')? 0:  (Integer)mi.in.get("FVDT")
    inFITN = (mi.in.get("FITN") == null || mi.in.get("FITN") == 'null')? "": mi.in.get("FITN")
    inTITN = (mi.in.get("TITN") == null || mi.in.get("TITN") == 'null')? "": mi.in.get("TITN")
    inFITG = (mi.in.get("FITG") == null || mi.in.get("FITG") == 'null')? "": mi.in.get("FITG")
    inTITG = (mi.in.get("TITG") == null || mi.in.get("TITG") == 'null')? "": mi.in.get("TITG")
    inFITC = (mi.in.get("FITC") == null || mi.in.get("FITC") == 'null')? "": mi.in.get("FITC")
    inTITC = (mi.in.get("TITC") == null || mi.in.get("TITC") == 'null')? "": mi.in.get("TITC")
    inFITT = (mi.in.get("FITT") == null || mi.in.get("FITT") == 'null')? "": mi.in.get("FITT")
    inTITT = (mi.in.get("TITT") == null || mi.in.get("TITT") == 'null')? "": mi.in.get("TITT")
    inFRSP = (mi.in.get("FRSP") == null || mi.in.get("FRSP") == 'null')? "": mi.in.get("FRSP")
    inTRSP = (mi.in.get("TRSP") == null || mi.in.get("TRSP") == 'null')? "": mi.in.get("TRSP")
    inSLFR = (mi.in.get("SLFR") == null || mi.in.get("SLFR") == 'null')? "": mi.in.get("SLFR")
    inSLTO = (mi.in.get("SLTO") == null || mi.in.get("SLTO") == 'null')? "": mi.in.get("SLTO")
    inFMAB = (mi.in.get("FMAB") == null || mi.in.get("FMAB") == 'null')? 0: (Integer)mi.in.get("FMAB")
    inTMAB = (mi.in.get("TMAB") == null || mi.in.get("TMAB") == 'null')? 0: (Integer)mi.in.get("TMAB")
    inFCHC = (mi.in.get("FCHC") == null || mi.in.get("FCHC") == 'null')? 0: (Integer)mi.in.get("FCHC")
    inTCHC = (mi.in.get("TCHC") == null || mi.in.get("TCHC") == 'null')? 0: (Integer)mi.in.get("TCHC")
    inFSUN = (mi.in.get("FSUN") == null || mi.in.get("FSUN") == 'null')? "": mi.in.get("FSUN")
    inTSUN = (mi.in.get("TSUN") == null || mi.in.get("TSUN") == 'null')? "": mi.in.get("TSUN")
    inFCUN = (mi.in.get("FCUN") == null || mi.in.get("FCUN") == 'null')? "": mi.in.get("FCUN")
    inTCUN = (mi.in.get("TCUN") == null || mi.in.get("TCUN") == 'null')? "": mi.in.get("TCUN")
    inFBGR = (mi.in.get("FBGR") == null || mi.in.get("FBGR") == 'null')? "": mi.in.get("FBGR")
    inTBGR = (mi.in.get("TBGR") == null || mi.in.get("TBGR") == 'null')? "": mi.in.get("TBGR")
    inFPRC = (mi.in.get("FPRC") == null || mi.in.get("FPRC") == 'null')? "": mi.in.get("FPRC")
    inTPRC = (mi.in.get("TPRC") == null || mi.in.get("TPRC") == 'null')? "": mi.in.get("TPRC")
    inFASS = (mi.in.get("FASS") == null || mi.in.get("FASS") == 'null')? "": mi.in.get("FASS")
    inTASS = (mi.in.get("TASS") == null || mi.in.get("TASS") == 'null')? "": mi.in.get("TASS")
    
    //validate fields
    if (!validateInputFields()) {
      return
    }
    
    doUpdateOPRICH()
    
  }
  /**
   * Validate input fields simliar to standard OIS017/F
   */
  def validateInputFields() {
    if (inPRRF == "")
    {
      mi.error("Price list code is mandatory")
      return false
    }
    if (inCUCD == "")
    {
      mi.error("Currency code is mandatory")
      return false
    }
    if (inFVDT == 0)
    {
      mi.error("Valid from date is mandatory")
      return false
    } else {
      if (inFVDT != 0 && !isDateValid(inFVDT.toString(), "yyyyMMdd")) {
      mi.error("Valid from date ${inFVDT} is invalid")
      return false
    }
    }
    if (inTITN.isBlank()) {
      inTITN = inTITN.padLeft(15, '\uFFEE')
    }
    
    if (inTITN.compareTo(inFITN) < 0) {
      mi.error("To value ${inTITN} less than from value ${inFITN}. See fields TITN and FITN")
      return false
    }
    if (inTITG.isBlank()) {
      inTITG = inTITG.padLeft(8, '\uFFEE')
    }
    if (inTITG.compareTo(inFITG) < 0) {
      mi.error("To value ${inTITG} less than from value ${inFITG}. See fields TITG and FITG")
      return false
    }
    if (inTITC.isBlank()) {
       inTITC = inTITC.padLeft(5, '\uFFEE')
    }
    if (inTITC.compareTo(inFITC) < 0) {
      mi.error("To value ${inTITC} less than from value ${inFITC}. See fields TITC and FITC")
      return false
    }
    if (inTITT.isBlank()) {
      inTITT = inTITT.padLeft(3, '\uFFEE')
    }
    if (inTITT.compareTo(inFITT) < 0) {
      mi.error("To value ${inTITT} less than from value ${inFITT}. See fields TITT and FITT")
      return false
    }
    if (inTRSP.isBlank()) {
      inTRSP = inTRSP.padLeft(10, '\uFFEE')
    }
    if (inTRSP.compareTo(inFRSP) < 0) {
      mi.error("To value ${inTRSP} less than from value ${inFRSP}. See fields TRSP and FRSP")
      return false
    }
    if (inSLTO.isBlank()) {
      inSLTO = inSLTO.padLeft(2, '\uFFEE')
    }
    if (inSLTO.compareTo(inSLFR) < 0) {
      mi.error("To value ${inSLTO} less than from value ${inSLFR}. See fields SLTO and SLFR")
      return false
    }
    if (inTMAB == 0) {
      inTMAB = 9
    } else {
      if (inTMAB < 1 || inTMAB > 2) {
        mi.error("Make buy code must be either blank, 1 or 2. Found value: ${inTMAB}")
        return false
      }
    }
    if (inFMAB == 0) {
    } else {
      if (inFMAB < 1 || inFMAB > 2) {
          mi.error("Make buy code must be either blank, 1 or 2. Found value: ${inFMAB}")
          return false
      }
    }
    
    if (inTMAB < inFMAB) {
      mi.error("To value ${inTMAB} less than from value ${inFMAB}. See fields TMAB and FMAB")
      return false
    }
    if (!(inFCHC >= 0 && inFCHC <= 5)) {
      mi.error("The value must be between 0(blank) and 5. Found value: ${inFCHC}")
      return false
    }
    if (!(inTCHC >= 0 && inTCHC <= 5)) {
      mi.error("The value must be between 0(blank) and 5. Found value: ${inTCHC}")
      return false
    }
    
    if (inTCHC < inFCHC) {
       mi.error("To value ${inTCHC} less than from value ${inFCHC}. See fields TCHC and FCHC")
       return false
    }
    if (inTSUN.isBlank()) {
      inTSUN = inTSUN.padLeft(10, '\uFFEE')
    }
    if (inTSUN.compareTo(inFSUN) < 0) {
      mi.error("To value ${inTSUN} less than from value ${inFSUN}. See fields TSUN and FSUN")
      return false
    }
    if (inTCUN.isBlank()) {
       inTCUN = inTCUN.padLeft(10, '\uFFEE')
    }
    if (inTCUN.compareTo(inFCUN) < 0) {
      mi.error("To value ${inTCUN} less than from value ${inFCUN}. See fields TCUN and FCUN")
      return false
    }
    if (inTBGR.isBlank()) {
       inTBGR = inTBGR.padLeft(2, '\uFFEE')
    }
    if (inTBGR.compareTo(inFBGR) < 0) {
      mi.error("To value ${inTBGR} less than from value ${inFBGR}. See fields TBGR and FBGR")
      return false
    }
    if (inTPRC.isBlank()) {
      inTPRC = inTPRC.padLeft(2, '\uFFEE')
    }
    if (inTPRC.compareTo(inFPRC) < 0) {
      mi.error("To value ${inTPRC} less than from value ${inFPRC}. See fields TPRC and FPRC")
      return false
    }
    if (inTASS.isBlank()) {
       inTASS = inTASS.padLeft(6, '\uFFEE')
    }
    if (inTASS.compareTo(inFASS) < 0) {
       mi.error("To value ${inTASS} less than from value ${inFASS}. See fields TASS and FASS")
      return false
    }
    
    return true
  }
  
  /**
   * Update Sales Price (OPRICH) record.
   */
  def doUpdateOPRICH() {
    int currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")).toInteger()
    int currentTime = Integer.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss")))
    Closure updateOPRICH = { LockedResult record ->
      record.set("OJFITN", inFITN)
      record.set("OJTITN", inTITN)
      record.set("OJFITG", inFITG)
      record.set("OJTITG", inTITG)
      record.set("OJFITC", inFITC)
      record.set("OJTITC", inTITC)
      record.set("OJFITT", inFITT)
      record.set("OJTITT", inTITT)
      record.set("OJFRSP", inFRSP)
      record.set("OJTRSP", inTRSP)
      record.set("OJSLFR", inSLFR)
      record.set("OJSLTO", inSLTO)
      record.set("OJFMAB", inFMAB)
      record.set("OJTMAB", inTMAB)
      record.set("OJFCHC", inFCHC)
      record.set("OJTCHC", inTCHC)
      record.set("OJFSUN", inFSUN)
      record.set("OJTSUN", inTSUN)
      record.set("OJFCUN", inFCUN)
      record.set("OJTCUN", inTCUN)
      record.set("OJFBGR", inFBGR)
      record.set("OJTBGR", inTBGR)
      record.set("OJFPRC", inFPRC)
      record.set("OJTPRC", inTPRC)
      record.set("OJFASS", inFASS)
      record.set("OJTASS", inTASS)
      record.set("OJLMDT", currentDate)
      record.set("OJCHID", program.getUser())
      record.set("OJCHNO", record.getInt("OJCHNO") + 1)
      record.update()
    }

    DBAction actionOPRICH = database.table("OPRICH").index("00").build()
    DBContainer OPRICH = actionOPRICH.getContainer()
    OPRICH.set("OJCONO", inCONO)
    OPRICH.set("OJPRRF", inPRRF)
    OPRICH.set("OJCUCD", inCUCD)
    OPRICH.set("OJCUNO", inCUNO)
    OPRICH.set("OJFVDT", inFVDT)

    if (!actionOPRICH.readLock(OPRICH, updateOPRICH)) { 
      mi.error("Record not found")
      return
    }
  }
  
  /**
   * Check if date is valid
   * @param date Date to check
   * @param format Format of the date
   * @return {@code true} if date is valid
   */
  public boolean isDateValid(String date, String format) {
    try {
      LocalDate.parse(date, DateTimeFormatter.ofPattern(format))
      return true
    } catch (DateTimeParseException e) {
      return false
    }
  }
}