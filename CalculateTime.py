## Calculate TS and TJ from log file
## Project 5 Group 74

def openFile() -> []:
        ## Returns the list of lines
        ## Change the file path below to the location of SearchTimeLog.txt
        fileName = input("Input the file path to SearchTimeLog.txt file: ")
        try:
                
                fileObject = open(fileName, "r")
                listOfLines = fileObject.readlines()
                fileObject.close()
                return listOfLines
                ## print(listOfLines)
                
        except:
                print("fileName at: " + fileName + " was not found")
                
def parseFile(listOfLines: []) -> (int):
        ## tokenize each line and get Servlet Response Times and JDBC execution times
        ## calculates the average time for each
        ## TS and TJ are measured in nanoseconds
        cumulativeTS = 0
        cumulativeTJ = 0
        numberOfLines = 0
        for lines in listOfLines:
                tokenList = lines.split(",")
                for token in tokenList:
                        timeList = token.split(":")
                        print(timeList)
                        if len(timeList) == 2:
                            if timeList[0].strip() == "JDBC Execution Time":
                                cumulativeTJ += int(timeList[1])
                            elif timeList[0].strip() == "Servlet Execution Time":
                                cumulativeTS += int(timeList[1])
                numberOfLines += 1
        return (cumulativeTS/numberOfLines, cumulativeTJ/numberOfLines)

def convertToMillis(infoTuple: ()):
    TS = infoTuple[0]/1000000
    TJ = infoTuple[1]/1000000
    print("TS in millis: " + str(TS) + " TJ in millis: " + str(TJ))
                        
                
if __name__ == "__main__":
    listOfLines = openFile()
    infoTuple = parseFile(listOfLines)
    print("TS: " + str(infoTuple[0]) + " TJ: " + str(infoTuple[1]))
    convertToMillis(infoTuple)
    
