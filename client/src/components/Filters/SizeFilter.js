import React, {useEffect} from 'react'
import { useCallback } from 'react';
import { useState } from 'react'


const SizeFilter = ({ size, hideTitle, multi=true, onChange}) => {

    const [appliedSize, setAppliedSize] = useState([]);
    const onClickDiv = useCallback((item) => {
        if (appliedSize.indexOf(item) > -1) {
            setAppliedSize(appliedSize?.filter(size => size !== item))
        } else {
            if(multi){
                setAppliedSize([...appliedSize,item])
            }else{
                setAppliedSize([item])
            }
        }
    }, [appliedSize, setAppliedSize])

    useEffect(()=>{
        onChange && onChange(appliedSize);
    },[appliedSize, onChange])

    return (
        <div className={`flex flex-col ${hideTitle?'':'mb-4'}`}>
            {!hideTitle && <p className='text-[16px] text-black mt-5 mb-5'>Size</p>}
            <div className='flex flex-wrap px-2'>
                {size?.map(item => {
                    return (
                        <div className='flex flex-col mr-2'>
                            <div className='w-[50px] h-8 text-center border rounded-lg mr-4 mb-4 pt-0.5 cursor-pointer 
                            hover:scale-105 bg-white border-gray-500 text-gray-500'
                            style={appliedSize.includes(item)?{background:'black', color:'white'}:{}}
                            onClick={() => onClickDiv(item)}>
                                {item}
                            </div>
                        </div>
                    )
                })}
            </div>
        </div>
    )
}

export default SizeFilter