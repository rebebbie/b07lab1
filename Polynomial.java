import java.lang.Math;

public class Polynomial{
	double[] coefficients;
	
	public Polynomial(){
		coefficients = new double[1];
		coefficients[0] = 0;
	}

	public Polynomial(double[] inputs){
		coefficients = new double[inputs.length];
		for(int i=0; i<inputs.length; i++){
			coefficients[i] = inputs[i];
		}
	}

	public Polynomial add(Polynomial poly){
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
	} 

	public double evaluate(double x){
		double ans = 0;
		
		for(int i=0; i<coefficients.length; i++){
			ans += coefficients[i]*Math.pow(x,i);
		}

		return ans;
	}

	public boolean hasRoot(double x){
		double ans = evaluate(x);

		if (ans==0.0){
			return true;
		}
		return false;
	}
}
