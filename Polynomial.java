import java.lang.Math;
import java.util.*;
import java.io.*;

public class Polynomial{
	double[] coefficients;
	int[] powers;
	
	public Polynomial(){
		coefficients = new double[1];
		coefficients[0] = 0;
		powers = new int[1];
		powers[0] = 0;
	}

	public Polynomial(double[] c, int[] p){
		coefficients = new double[c.length];
		for(int i=0; i<c.length; i++){
			coefficients[i] = c[i];
		}
		powers = new int[p.length];
		for(int i=0; i<p.length; i++){
			powers[i] = p[i];
		}
	}
	
	public Polynomial(File f) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line = br.readLine();
		ArrayList<String> termCoeff = new ArrayList<String>();
		ArrayList<String> termPower = new ArrayList<String>();
		String cur = "";
		for(int i=0; i<line.length(); i++) {
			cur += line.charAt(i);
			if(i!=line.length()-1) {
				if(line.charAt(i+1) == 'x') {
					termCoeff.add(cur);
					i++;
					cur = "";
					while(line.charAt(i) != '-' && line.charAt(i) != '+') {
						cur += line.charAt(i);
						i++;
					}
					termPower.add(cur);
				}
				else if (line.charAt(i+1) == '-' || line.charAt(i+1) == '+') {
					i++;
					cur = ""+line.charAt(i+1);
				}
			}
		}
		/*
		List<String[]> termList = new ArrayList<String[]>();
		
		/*
		System.out.println(line);
		String term = "";
		for(int i=line.length()-1; i>=0; i--){
			term = line.charAt(i)+term;
			if(line.charAt(i) == '-' || line.charAt(i) == '+'){
				termList.add(term.split("x"));
				if(termList.get(termList.size()-1).length == 1){
					termList.set(termList.get(termList.size()-1),{termList.get(termList.size()-1)[0],0});
				} 
				term = "";
			}
		}
		
		List<Double> termCoeff = new ArrayList<Double>;
		List<Integer> termPow = new ArrayList<Integer>;
		for(int i=0; i<termList.size(); i++){
			termCoeff.add(termList.get(i)[0]);
			termPow.add(termList.get(i)[1]);
		}

		coefficients = new double[termCoeff.size()];
		for(int i=0; i<termCoeff.size(); i++){
			newCoeff[i] = parseDouble(termCoeff.get(i));
		}
		powers = new int[termPower.size()];
		for(int i=0; i<termCoeff.size(); i++){
			newPower[i] = parseInt(termPower.get(i));
		}
		coeff.toArray(newCoeff);
		power.toArray(newPower);
		*/

		coefficients = new double[termCoeff.size()];
		for(int i=0; i<termCoeff.size(); i++){
			coefficients[i] = Double.parseDouble(termCoeff.get(i));
		}
		powers = new int[termPower.size()];
		for(int i=0; i<termCoeff.size(); i++){
			powers[i] = Integer.parseInt(termPower.get(i));
		}
	}

	public Polynomial add(Polynomial poly){
		ArrayList<Double> listCoeff = new ArrayList<Double>();
		ArrayList<Integer> listPow = new ArrayList<Integer>();
		//ArrayList<String[]> sumTerms = new ArrayList<String[]>();
		//List<int[]> listPow = Arrays.asList(powers); 
		
		for(int i=0; i<poly.powers.length; i++) {
			for(int j=0; j<powers.length; j++) {
				if(powers[i] == powers[j]) {
					listCoeff.add(coefficients[j]+poly.coefficients[i]);
					listPow.add(powers[j]+poly.powers[i]);
				}
				else if(j == powers.length-1) { //no matches
					//adds both of them into the final one
					listCoeff.add(poly.coefficients[i]);
					listPow.add(poly.powers[i]);
					//listCoeff.add(coefficients[j]);
					//listPow.add(powers[j]);
				}
			}
		}
		for(int i=0; i<powers.length; i++) {
			if(!listPow.contains(powers[i])){ //missing terms from original polynomial
				listCoeff.add(coefficients[i]);
				listPow.add(powers[i]);
			}
		}
/*
		for(int i=0; i<poly.powers.length; i++){
			for(int j=0; j<powers.length; i++){
				if(powers[i] == powers[j]){
					listCoeff.set(j, listCoeff.get(j)+coefficients[i]);
					//listCoeff.get(j).set(listCoeff.) += coefficients[i]; 
				}
				else if(j == powers.length-1){ //no matches
					listPow.add(powers[j]); //appends
					listCoeff.add(coefficients[j]);
				}
			}
		}
*/
		double[] newCoeff = new double[listCoeff.size()];
		for(int i=0; i<listCoeff.size(); i++){
			newCoeff[i] = listCoeff.get(i);
		}
		int[] newPower = new int[listPow.size()];
		for(int i=0; i<listCoeff.size(); i++){
			newPower[i] = listPow.get(i);
		}
		//coeff.toArray(newCoeff);
		//power.toArray(newPower);

		Polynomial newPoly = new Polynomial(newCoeff, newPower);
		return newPoly;

/*
		if(poly.length > coefficients.length){
			for(int i=0; i<coefficients.length; i++){
				poly.coefficients[i] += coefficients[i];
			}
		}
		else
		{
			for(int i=0; i<poly.length; i++){
				poly.coefficients[i] += coefficients[i];
			}
		}
		return poly;
*/
	}  

	public double evaluate(double x){
		double ans = 0;
		
		for(int i=0; i<coefficients.length; i++){
			ans += coefficients[i]*Math.pow(x,powers[i]);
		}

		return ans;
	}

	public boolean hasRoot(double x){
		double ans = evaluate(x);

		if (ans==0){
			return true;
		}
		return false;
	}

	public Polynomial reduce(ArrayList<Double> coeff, ArrayList<Integer> power){
		for(int i=0; i<power.size(); i++){
			for(int j=0; j<power.size(); j++){
				if(i != j && power.get(i)==power.get(j)){
					coeff.set(i,coeff.get(i)+coeff.get(j));
					coeff.remove(j);
					power.remove(j);
				}
			}
		}
		double[] newCoeff = new double[coeff.size()];
		for(int i=0; i<coeff.size(); i++){
			newCoeff[i] = coeff.get(i);
		}
		int[] newPower = new int[power.size()];
		for(int i=0; i<coeff.size(); i++){
			newPower[i] = power.get(i);
		}
		//coeff.toArray(newCoeff);
		//power.toArray(newPower);
		Polynomial newPoly = new Polynomial(newCoeff, newPower);
		
		return newPoly;
	} 

	public Polynomial multiply(Polynomial poly){
		List<Double> listCoeff = new ArrayList<>();
		List<Integer> listPow = new ArrayList<>();
		for(int i=0; i<poly.coefficients.length; i++){
			for(int j=0; j<coefficients.length; j++){
				listCoeff.add(poly.coefficients[i]*coefficients[j]);
				listPow.add(poly.powers[i]+powers[j]);
				
				//Polynomial term = new Polynomial(poly.coefficients[i]*coefficients[j], poly.powers[i]*powers[j]);
				//add(term);
			}
		}
		double[] newCoeff = new double[listCoeff.size()];
		for(int i=0; i<listCoeff.size(); i++){
			newCoeff[i] = listCoeff.get(i);
		}
		int[] newPower = new int[listPow.size()];
		for(int i=0; i<listCoeff.size(); i++){
			newPower[i] = listPow.get(i);
		}

		Polynomial newPoly = new Polynomial(newCoeff, newPower);
		return newPoly;
	}

	public void saveToFile(String fileName) throws IOException{
		FileWriter output = new FileWriter(fileName, false);
		
		if(coefficients[0] < 0) output.write("-"+coefficients[0]+"x"+powers[0]);
		else output.write(coefficients[0]+"x"+powers[0]);
		
		for(int i=1; i<coefficients.length; i++){
			if(coefficients[i] < 0) output.write("-");
			output.write(coefficients[i]+"x"+powers[i]);
		}
	}
}