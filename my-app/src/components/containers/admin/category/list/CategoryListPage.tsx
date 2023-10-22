import {useEffect, useState} from "react";
import {ICategoryItem} from "./types.ts";
import http_common from "../../../../../http_common.ts";

const CategoryListPage = () => {
    const [list, setList] = useState<ICategoryItem[]>([]);

    const getData = () => {
        http_common
            .get<ICategoryItem[]>("/")
            .then(resp => {
                setList(resp.data)
            });
    }
    useEffect(()=>{
        getData();
    });
    const content = list.map(item=>(
        <tr className="bg-white border-b dark:bg-gray-800 dark:border-gray-700" key={item.id}>
            <th scope="row"
                className="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                {item.name}
            </th>
            <td className="px-6 py-4">
                {item.image}
            </td>
            <td className="px-6 py-4">
                {item.description}
            </td>
            <td className="px-6 py-4">
                <button
                    className="bg-transparent hover:bg-blue-500 text-blue-700 font-semibold hover:text-white py-2 px-4 border border-blue-500 hover:border-transparent rounded">
                    Змінить
                </button>

                <button
                    className="ml-2 bg-transparent hover:bg-red-500 text-red-700 font-semibold hover:text-white py-2 px-4 border border-red-500 hover:border-transparent rounded">
                    Видалить
                </button>
            </td>
        </tr>
    ))
    return (
        <>
            <div className="mx-auto max-w-2xl lg:text-center">
                <h1 className={"mt-2 text-3xl font-bold tracking-tight text-gray-900 sm:text-4xl"}>
                    Список категорій
                </h1>
            </div>

            <div className="mt-6 relative overflow-x-auto">

                <table className="w-full text-sm text-left text-gray-500 dark:text-gray-400">
                    <thead className="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                    <tr>
                        <th scope="col" className="px-6 py-3">
                            Назва
                        </th>
                        <th scope="col" className="px-6 py-3">
                            Фото
                        </th>
                        <th scope="col" className="px-6 py-3">
                            Опис
                        </th>
                        <th scope="col" className="px-6 py-3">

                        </th>
                    </tr>
                    </thead>
                    <tbody>
                        {content}
                    </tbody>
                </table>
            </div>

        </>
    )
}

export default CategoryListPage;