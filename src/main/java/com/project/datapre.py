import numpy as np
import pandas as pd
import sys
from sklearn.preprocessing import OneHotEncoder
import os

def OHot(df):
    ctg = df.select_dtypes(include=['object']).columns.tolist()
    oneh = OneHotEncoder(sparse_output=False)
    temp = pd.DataFrame(oneh.fit_transform(df[ctg]),columns = oneh.get_feature_names_out(ctg))
    df = pd.concat([temp,df.drop(ctg,axis=1)],axis=1)
    return df

def svd_impute(matrix, max_iter=100, tol=1e-5):
    matrix = np.array(matrix)
    missing_mask = np.isnan(matrix)
    
    col_mean = np.nanmean(matrix, axis=0)
    inds = np.where(np.isnan(matrix))
    matrix[inds] = np.take(col_mean, inds[1])

    for _ in range(max_iter):
        U, s, Vt = np.linalg.svd(matrix, full_matrices=False)
        S = np.diag(s)
        matrix_hat = np.dot(U, np.dot(S, Vt))
        
        matrix[missing_mask] = matrix_hat[missing_mask]
        if np.linalg.norm(matrix[missing_mask] - matrix_hat[missing_mask]) < tol:
            break

    pd.DataFrame(df).to_csv("D:\\Working Folder\\Desktop\\Kaggle\\Project-1\\project\\src\\main\\java\\com\\project\\temp_data.csv",index=False)



def drop(df,col):
    df.drop(col,axis=1,inplace=True)
    df.to_csv("D:\\Working Folder\\Desktop\\Kaggle\\Project-1\\project\\src\\main\\java\\com\\project\\temp_data.csv",index=False)


def dropall(df):
    for cols in df.items():
        if(df[cols[0]].nunique()>20 and df[cols[0]].dtype=='object'):
            df.drop(cols[0],axis=1,inplace=True)
    df.to_csv("D:\\Working Folder\\Desktop\\Kaggle\\Project-1\\project\\src\\main\\java\\com\\project\\temp_data.csv",index=False)






df = pd.read_csv("D:\\Working Folder\\Desktop\\Kaggle\\Project-1\\project\\src\\main\\java\\com\\project\\temp_data.csv")
if(len(sys.argv)<2):
    sys.exit(1)

inp = list(sys.argv[1].split(' '))

if(inp[0]=="drop"):
    drop(df,inp[1])
elif(inp[0]=="dropall"):
    dropall(df)
elif(inp[0]=="svd"):
    df = OHot(df)
    svd_impute(df)


print("asd")

