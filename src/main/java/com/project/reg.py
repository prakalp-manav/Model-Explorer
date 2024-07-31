import pandas as pd
import sys
from sklearn.model_selection import train_test_split
from sklearn.linear_model import LinearRegression
from sklearn.preprocessing import OneHotEncoder
# from sklearn.naive_bayes import GaussianNB
from sklearn.neighbors import KNeighborsRegressor
from sklearn.tree import DecisionTreeRegressor
from sklearn.tree import ExtraTreeRegressor
from sklearn.svm import SVR
from sklearn.ensemble import RandomForestRegressor



def OHot(df):
    ctg = df.select_dtypes(include=['object']).columns.tolist()
    oneh = OneHotEncoder(sparse_output=False)
    temp = pd.DataFrame(oneh.fit_transform(df[ctg]),columns = oneh.get_feature_names_out(ctg))
    df = pd.concat([temp,df.drop(ctg,axis=1)],axis=1)
    return df

if(len(sys.argv)<2):
    sys.exit(1)

csv_file = sys.argv[1]
if csv_file=="none":
    print("asdasd")
    df = pd.read_csv("D:\\Working Folder\\Desktop\\Kaggle\\Project-1\\project\\src\\main\\java\\com\\project\\temp_data.csv")
else:
    df = pd.read_csv(csv_file)
df = OHot(df)

X_train, X_test, Y_train, Y_test = train_test_split(df.iloc[:,:-1],df.iloc[:,-1],test_size=0.2,random_state=0)


lr = LinearRegression()
model_lr = lr.fit(X_train,Y_train)
print("lr")
print(model_lr.score(X_test,Y_test))

ex = ExtraTreeRegressor()
model_ex = ex.fit(X_train,Y_train)
print("ex")
print(model_ex.score(X_test,Y_test))

k = KNeighborsRegressor()
model_k = k.fit(X_train,Y_train)
print("k")
print(model_k.score(X_test,Y_test))

d = DecisionTreeRegressor()
model_d = d.fit(X_train,Y_train)
print("d")
print(model_d.score(X_test,Y_test))

sv = SVR()
model_sv = sv.fit(X_train,Y_train)
print("sv")
print(model_sv.score(X_test,Y_test))

r = RandomForestRegressor()
model_r = r.fit(X_train,Y_train)
print("r")
print(model_r.score(X_test,Y_test))
