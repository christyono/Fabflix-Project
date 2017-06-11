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
                        if timeList[0] == "JDBC Execution Time":
                            cumulativeTJ += int(timeList[1])
                        else:
                            cumulativeTS += int(timeList[1].replace("\n", ""))
                numberOfLines += 1
        return (cumulativeTS/numberOfLines, cumulativeTJ/numberOfLines)
    

                        
                
if __name__ == "__main__":
    listOfLines = openFile()
    infoTuple = parseFile(listOfLines)
    print("TS: " + str(infoTuple[0]) + " TJ: " + str(infoTuple[1]))
    
