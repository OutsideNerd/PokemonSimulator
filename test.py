from Queue import PriorityQueue

def main():
    build_a_graph()
    return 1

def build_a_graph():
    d0 = {"name": "A", "SLDG": 20}
    d1 = {"name": "B", "SLDG": 15}
    d2 = {"name": "C", "SLDG": 10}
    d3 = {"name": "D", "SLDG": 15}
    d4 = {"name": "E", "SLDG": 7}
    d5 = {"name": "F", "SLDG": 3}
    d6 = {"name": "G", "SLDG":0}
    ##Add costs as tuples
    d0["vertices"] = [(d1, 7), (d2, 8), (d3, 9)]
    d1["vertices"] = [(d4, 5)]
    d2["vertices"] = [(d4, 9)]
    d3["vertices"] = [(d5, 10), (d6, 13)]
    d4["vertices"] = [(d5, 7), (d6, 10)]
    d5["vertices"] = [(d6, 4)]
    d6["vertices"] = []
    a_star_search(d0, d6[name])
    
    d0 = {"name": "A", "SLDG": 50}
    d1 = {"name": "B", "SLDG": 15}
    d2 = {"name": "C", "SLDG": 10}
    d3 = {"name": "D", "SLDG": 15}
    d4 = {"name": "E", "SLDG": 0}
    ##Add costs as tuples
    d0["vertices"] = [(d1, 70), (d2, 18), (d3, 29)]
    d1["vertices"] = [(d4, 15)]
    d2["vertices"] = [(d3, 19)]
    d3["vertices"] = [(d4, 18)]
    d4["vertices"] = []
    a_star_search(d0, d4[name])
    
    d0 = {"name": "A", "SLDG": 200}
    d1 = {"name": "B", "SLDG": 150}
    d2 = {"name": "C", "SLDG": 100}
    d3 = {"name": "D", "SLDG": 150}
    d4 = {"name": "E", "SLDG": 17}
    d5 = {"name": "F", "SLDG": 0}
    ##Add costs as tuples
    d0["vertices"] = [(d1, 700), (d2, 800), (d3, 900)]
    d1["vertices"] = [(d4, 500)]
    d2["vertices"] = [(d4, 950)]
    d3["vertices"] = [(d5, 1070)]
    d4["vertices"] = [(d5, 780)]
    d5["vertices"] = []
    a_star_search(d0, d5[name])
    
    d0 = {"name": "A", "SLDG": 4}
    d1 = {"name": "B", "SLDG": 3}
    d2 = {"name": "C", "SLDG": 3}
    d3 = {"name": "D", "SLDG": 2}
    d4 = {"name": "E", "SLDG": 1}
    d5 = {"name": "F", "SLDG": 2}
    d6 = {"name": "G", "SLDG": 0}
    ##Add costs as tuples
    d0["vertices"] = [(d1, 7), (d2, 8), (d3, 9)]
    d1["vertices"] = [(d4, 5)]
    d2["vertices"] = [(d4, 9)]
    d3["vertices"] = [(d5, 10), (d6, 13)]
    d4["vertices"] = [(d5, 7), (d6, 10)]
    d5["vertices"] = [(d6, 4)]
    d6["vertices"] = []
    a_star_search(d0, d6[name])
    d0 = {"name": "A", "SLDG": 20}
    d1 = {"name": "B", "SLDG": 15}
    d2 = {"name": "C", "SLDG": 10}
    d3 = {"name": "D", "SLDG": 15}
    d4 = {"name": "E", "SLDG": 7}
    d5 = {"name": "F", "SLDG": 0}
    ##Add costs as tuples
    d0["vertices"] = [(d1, 7), (d2, 8)]
    d1["vertices"] = [(d3, 5)]
    d2["vertices"] = [(d4, 9)]
    d3["vertices"] = [(d5, 10)]
    d4["vertices"] = [(d5, 7)]
    d5["vertices"] = []
    a_star_search(d0, d5[name])    
    return 1

def a_star_search(start, end):
    fringe = PriorityQueue(0)
    fringe.put(start, 0)
    costTot = {}
    path[0] = Start
    costTot[0] = 0
    total = 0
    while not fringe.empty():
        current = fringe.get()
        path[i] = current[name]
        costTot[i] = current[vertices[i][1]]        
        if current[name] == end:
            break
        for i in range(current[vertices.len]): ##not done
                priority = costTot[i-1] + current[vertices[i][1]] + current[SLDG]
                fringe.put(current[vertice[i][0]], priority)
    wholepath = ""
    for i in range(path.len):
        wholepath = wholepath + path[i]
        total = total + costTot[i]                                    
    print "The whole path was %s and the total cost was %d", wholepath, 
    return 1
    
    
main()